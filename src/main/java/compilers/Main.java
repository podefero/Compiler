package compilers;

import compilers.antlr.KxiLexer;
import compilers.antlr.KxiParser;
import compilers.ast.assembly.AssemblyMain;
import compilers.ast.intermediate.InterGlobal;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.commandargs.ArgumentFlags;
import compilers.exceptions.ParseException;
import compilers.exceptions.ParserErrorListener;
import compilers.util.InputHandler;
import compilers.util.OutputHandler;
import compilers.visitor.antlr.AntlrToKxiVisitor;
import compilers.visitor.assembly.AssemblyAssembleVisitor;
import compilers.visitor.assembly.InterToAssemblyVisitor;
import compilers.visitor.generic.GraphVizVisitor;
import compilers.visitor.intermediate.BreakAndReturnsVisitor;
import compilers.visitor.intermediate.InterSymbolTableVisitor;
import compilers.visitor.intermediate.KxiToIntermediateVisitor;
import compilers.visitor.kxi.invalid_break.InvalidBreakVisitor;
import compilers.visitor.kxi.invalid_write.InvalidWriteVisitor;
import compilers.visitor.kxi.symboltable.ScopeHandler;
import compilers.visitor.kxi.symboltable.SymbolTable;
import compilers.visitor.kxi.symboltable.SymbolTableVisitor;
import compilers.visitor.kxi.typecheck.TypeCheckerVisitor;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

        // Get the token stream
        CommonTokenStream tokens = new CommonTokenStream(kxiLexer);

        if (argumentFlags.printTokens) {
            try {
                printTokens(kxiLexer, outputHandler);
            } catch (IOException e) {
                //print to stdout if file is not found
                System.out.println(e.getMessage());
            }
        }
        if (argumentFlags.parseTree) parseTree(new CommonTokenStream(kxiLexer), argumentFlags, outputHandler);
    }

    static void printTokens(KxiLexer kxiLexer, OutputHandler outputHandler) throws IOException {
        outputHandler.outputLex(kxiLexer.printTokens());
    }

    static void parseTree(CommonTokenStream tokenStream, ArgumentFlags argumentFlags, OutputHandler outputHandler) {
        KxiParser parser = new KxiParser(tokenStream);

        KxiMain kxiMain = null;
        try {
            AntlrToKxiVisitor antlrToKxiVisitor = new AntlrToKxiVisitor();
            antlrToKxiVisitor.visitCompilationUnit(parser.compilationUnit());
            kxiMain = (KxiMain) antlrToKxiVisitor.getRootNode();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
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
            symbolTableVisitor.dumpErrorStack();
        }

        TypeCheckerVisitor typeCheckerVisitor = new TypeCheckerVisitor(symbolTableVisitor.getScopeHandler(), new Stack<>());
        rootNode.accept(typeCheckerVisitor);
        if (typeCheckerVisitor.hasErrors()) {
            hasError = true;
            typeCheckerVisitor.dumpErrorStack();
        }

        InvalidWriteVisitor invalidWriteVisitor = new InvalidWriteVisitor(new Stack<>(), symbolTableVisitor.getScopeHandler());
        rootNode.accept(invalidWriteVisitor);
        if (invalidWriteVisitor.hasErrors()) {
            hasError = true;
            invalidWriteVisitor.dumpErrorStack();
        }

        InvalidBreakVisitor invalidBreakVisitor = new InvalidBreakVisitor();
        rootNode.accept(invalidBreakVisitor);
        if (invalidBreakVisitor.hasErrors()) {
            hasError = true;
            invalidBreakVisitor.dumpErrorStack();
        }

        if (argumentFlags.compile && !hasError) {
            try {
                compile(rootNode, outputHandler, symbolTableVisitor.getScopeHandler());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void compile(AbstractKxiNode rootNode, OutputHandler outputHandler, ScopeHandler scopeHandler) throws IOException {
        BreakAndReturnsVisitor breakAndReturnsVisitor = new BreakAndReturnsVisitor();
        rootNode.accept(breakAndReturnsVisitor);


        KxiToIntermediateVisitor kxiToIntermediateVisitor = new KxiToIntermediateVisitor(scopeHandler);
        rootNode.accept(kxiToIntermediateVisitor);

        InterSymbolTableVisitor interSymbolTableVisitor =
                new InterSymbolTableVisitor(new InterSymbolTable(new HashMap<>(), new HashMap<>()), null);

        InterGlobal interGlobal = kxiToIntermediateVisitor.getRootNode();
        interGlobal.accept(interSymbolTableVisitor);

        InterToAssemblyVisitor interToAssemblyVisitor = new InterToAssemblyVisitor(new ArrayList<>()
                , null
                , interSymbolTableVisitor.getInterSymbolTable()
                , interSymbolTableVisitor.getInterSymbolTable().getFunctionDataMap().get("main$main"));

        interGlobal.accept(interToAssemblyVisitor);


        AssemblyAssembleVisitor assemblyAssembleVisitor = new AssemblyAssembleVisitor();
        AssemblyMain assemblyMain = interToAssemblyVisitor.getRootNode();

        assemblyMain.accept(assemblyAssembleVisitor);

    }

}
