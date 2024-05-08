package compilers;

import compilers.antlr.KxiLexer;
import compilers.antlr.KxiParser;
import compilers.ast.assembly.AssemblyMain;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.commandargs.ArgumentFlags;
import compilers.util.InputHandler;
import compilers.util.OutputHandler;
import compilers.visitor.antlr.AntlrToKxiVisitor;
import compilers.visitor.assembly.AssemblyAssembleVisitor;
import compilers.visitor.assembly.ExpressionToAssemblyVisitor;
import compilers.visitor.assembly.StatementsToAssemblyVisitor;
import compilers.visitor.generic.GraphVizVisitor;
import compilers.visitor.intermediate.BreakAndReturnsVisitor;
import compilers.visitor.intermediate.ExpressionToTempVisitor;
import compilers.visitor.intermediate.FullyLoadedIdVisitor;
import compilers.visitor.intermediate.InterSymbolTableVisitor;
import compilers.visitor.kxi.invalid_break.InvalidBreakVisitor;
import compilers.visitor.kxi.invalid_write.InvalidWriteVisitor;
import compilers.visitor.kxi.symboltable.ScopeHandler;
import compilers.visitor.kxi.symboltable.SymbolTableVisitor;
import compilers.visitor.kxi.typecheck.TypeCheckerVisitor;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        //parse the command line args
        ArgumentFlags argumentFlags = new ArgumentFlags(args);
        InputHandler inputHandler = new InputHandler(argumentFlags.inputFileName);
        OutputHandler outputHandler = new OutputHandler(argumentFlags.outputFileName);

        //call function stubs based on arguments
        if (argumentFlags.lexing) lexing(argumentFlags, inputHandler, outputHandler);
    }

    static void lexing(ArgumentFlags argumentFlags, InputHandler inputHandler, OutputHandler outputHandler) {
        KxiLexer kxiLexer = new KxiLexer(inputHandler.fileToCharStream());
        CommonTokenStream commonTokenStream = new CommonTokenStream(kxiLexer);
        if (argumentFlags.printTokens) {
            try {
                printTokens(outputHandler, commonTokenStream);
            } catch (IOException e) {
                //print to stdout if file is not found
                //System.out.println(e.getMessage());
            }
        }
        if (argumentFlags.parseTree) parseTree(commonTokenStream, argumentFlags, outputHandler);
    }

    private static String printTokens(CommonTokenStream tokens) {
        //CommonTokenStream tokens = new CommonTokenStream(this);
        tokens.fill();
        List<Token> tokenList = tokens.getTokens();
        StringBuilder stringBuilder = new StringBuilder();
        for (Token token : tokenList) {
            String tokenName = KxiLexer.VOCABULARY.getSymbolicName(token.getType());
            //stringBuilder.append("Line#: " + token.getLine() + ":" + token.getCharPositionInLine() + " " + token.getText() + " " + tokenName + "\n");
            stringBuilder.append(tokenName + " " + token.getLine() + " " + token.getText() + "\n");
        }
        return stringBuilder.toString();
    }

    static void printTokens(OutputHandler outputHandler, CommonTokenStream commonTokenStream) throws IOException {
        outputHandler.outputLex(printTokens(commonTokenStream));
    }

    static void parseTree(CommonTokenStream tokenStream, ArgumentFlags argumentFlags, OutputHandler outputHandler) {
        KxiParser parser = new KxiParser(tokenStream);
        KxiMain kxiMain = null;
        AntlrToKxiVisitor antlrToKxiVisitor = new AntlrToKxiVisitor();

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new RuntimeException("Syntax error at line " + line + ":" + charPositionInLine + " " + msg);
            }
        });

        try {
            antlrToKxiVisitor.visitCompilationUnit(parser.compilationUnit());
            kxiMain = (KxiMain) antlrToKxiVisitor.getRootNode();
        } catch (RuntimeException ex) {
            if (argumentFlags.printASTDiagram) {
                System.err.println(ex.getMessage());
            }
            return;
        }

        if (argumentFlags.printASTDiagram) {
            try {
                printASTDiagram(kxiMain, outputHandler);
            } catch (IOException e) {
                System.out.println("Need .dot file to output graphVis");
            }
        }

        if (argumentFlags.semantics) semantics(kxiMain, argumentFlags, outputHandler);
    }

    static void printASTDiagram(KxiMain kxiMain, OutputHandler outputHandler) throws IOException {
        GraphVizVisitor graphVizVisitor = new GraphVizVisitor(kxiMain);
        outputHandler.outputAST(graphVizVisitor.getGraph());
    }

    static void semantics(AbstractKxiNode rootNode, ArgumentFlags argumentFlags, OutputHandler outputHandler) {
        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();
        boolean hasError = false;
        rootNode.accept(symbolTableVisitor);
        if (symbolTableVisitor.hasErrors()) {
            hasError = true;
        }

        TypeCheckerVisitor typeCheckerVisitor = new TypeCheckerVisitor(symbolTableVisitor.getScopeHandler(), new Stack<>());
        rootNode.accept(typeCheckerVisitor);
        if (typeCheckerVisitor.hasErrors()) {
            hasError = true;
        }

        InvalidWriteVisitor invalidWriteVisitor = new InvalidWriteVisitor(new Stack<>(), symbolTableVisitor.getScopeHandler());
        rootNode.accept(invalidWriteVisitor);
        if (invalidWriteVisitor.hasErrors()) {
            hasError = true;
        }

        InvalidBreakVisitor invalidBreakVisitor = new InvalidBreakVisitor();
        rootNode.accept(invalidBreakVisitor);
        if (invalidBreakVisitor.hasErrors()) {
            hasError = true;
        }

        if (argumentFlags.printSemanticInformation)
            printSemanticInfo(symbolTableVisitor, typeCheckerVisitor, invalidWriteVisitor, invalidBreakVisitor);

        if (argumentFlags.compile && !hasError) {
            try {
                compile(rootNode, outputHandler, symbolTableVisitor.getScopeHandler());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void printSemanticInfo(SymbolTableVisitor symbolTableVisitor, TypeCheckerVisitor typeCheckerVisitor, InvalidWriteVisitor invalidWriteVisitor, InvalidBreakVisitor invalidBreakVisitor) {
        symbolTableVisitor.dumpErrorStack();
        typeCheckerVisitor.dumpErrorStack();
        invalidBreakVisitor.dumpErrorStack();
        invalidWriteVisitor.dumpErrorStack();
    }

    static void compile(AbstractKxiNode rootNode, OutputHandler outputHandler, ScopeHandler scopeHandler) throws IOException {

        BreakAndReturnsVisitor breakAndReturnsVisitor = new BreakAndReturnsVisitor();
        rootNode.accept(breakAndReturnsVisitor);

        rootNode.accept(new FullyLoadedIdVisitor(scopeHandler));


        ExpressionToTempVisitor expressionToTempVisitor = new ExpressionToTempVisitor();
        rootNode.accept(expressionToTempVisitor);


        InterSymbolTableVisitor interSymbolTableVisitor =
                new InterSymbolTableVisitor(new InterSymbolTable(new HashMap<>(), new HashMap<>(), new HashMap<>()), null, expressionToTempVisitor.tempVars);

        rootNode.accept(interSymbolTableVisitor);

        ExpressionToAssemblyVisitor expressionToAssemblyVisitor = new ExpressionToAssemblyVisitor(interSymbolTableVisitor.getInterSymbolTable()
                , interSymbolTableVisitor.getInterSymbolTable().getFunctionDataMap().get("main$main"));

        rootNode.accept(expressionToAssemblyVisitor);

        StatementsToAssemblyVisitor statementsToAssemblyVisitor = new StatementsToAssemblyVisitor(new ArrayList<>(), new ArrayList<>()
                , interSymbolTableVisitor.getInterSymbolTable()
                , interSymbolTableVisitor.getInterSymbolTable().getFunctionDataMap().get("main$main")
                , null);

        rootNode.accept(statementsToAssemblyVisitor);

        AssemblyAssembleVisitor assemblyAssembleVisitor = new AssemblyAssembleVisitor();
        AssemblyMain assemblyMain = statementsToAssemblyVisitor.getRootNode();

        assemblyMain.accept(assemblyAssembleVisitor);

        outputHandler.outputAsm(assemblyAssembleVisitor.getAsm());

    }

}
