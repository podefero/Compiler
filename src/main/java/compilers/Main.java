package compilers;

import compilers.antlr.KxiLexer;
import compilers.antlr.KxiParser;
import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.InterMain;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.commandargs.ArgumentFlags;
import compilers.exceptions.ParseException;
import compilers.exceptions.ParserErrorListener;
import compilers.util.InputHandler;
import compilers.util.OutputHandler;
import compilers.visitor.antlr.AntlrToKxiVisitor;
import compilers.visitor.generic.GraphVizVisitor;
import compilers.visitor.intermediate.KxiToIntermediateVisitor;
import compilers.visitor.kxi.invalid_break.InvalidBreakVisitor;
import compilers.visitor.kxi.invalid_write.InvalidWriteVisitor;
import compilers.visitor.kxi.symboltable.SymbolTable;
import compilers.visitor.kxi.symboltable.SymbolTableVisitor;
import compilers.visitor.kxi.typecheck.TypeCheckerVisitor;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
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
        System.out.println("Lexing");
        KxiLexer kxiLexer = new KxiLexer(inputHandler.fileToCharStream());

        if (argumentFlags.printTokens) {
            try {
                printTokens(kxiLexer, outputHandler);
            } catch (IOException e) {
                //print to stdout if file is not found
                System.out.println(kxiLexer.printTokens());
            }
        }
        if (argumentFlags.parseTree) parseTree(new CommonTokenStream(kxiLexer), argumentFlags, outputHandler);
    }

    static void printTokens(KxiLexer kxiLexer, OutputHandler outputHandler) throws IOException {
        outputHandler.outputLex(kxiLexer.printTokens());
    }

    static void parseTree(CommonTokenStream tokenStream, ArgumentFlags argumentFlags, OutputHandler outputHandler) {
        KxiParser parser = new KxiParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new ParserErrorListener());

        //catch any syntax errors
        try {
            parser.compilationUnit();
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        AntlrToKxiVisitor antlrToKxiVisitor = new AntlrToKxiVisitor();
        antlrToKxiVisitor.visitCompilationUnit(parser.compilationUnit());

        KxiMain kxiMain = (KxiMain) antlrToKxiVisitor.getRootNode();

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

        if(argumentFlags.compile && !hasError) {
            try {
                compile(rootNode, outputHandler);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void compile(AbstractKxiNode rootNode, OutputHandler outputHandler) throws IOException {
        KxiToIntermediateVisitor kxiToIntermediateVisitor = new KxiToIntermediateVisitor();
        rootNode.accept(kxiToIntermediateVisitor);
        InterMain interRoot = (InterMain) kxiToIntermediateVisitor.getRootNode();
       // outputHandler.outputAsm(interRoot.getJmpToMain().gatherAssembly());
    }

}
