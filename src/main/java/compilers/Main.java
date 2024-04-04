package compilers;

import compilers.antlr.KxiLexer;
import compilers.antlr.KxiParser;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.commandargs.ArgumentFlags;
import compilers.util.InputHandler;
import compilers.util.OutputHandler;
import compilers.visitor.antlr.AntlrToKxiVisitor;
import compilers.visitor.generic.GraphVizVisitor;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //parse the command line args
        ArgumentFlags argumentFlags = new ArgumentFlags(args);
        InputHandler inputHandler = new InputHandler(argumentFlags.inputFileName);
        OutputHandler outputHandler = new OutputHandler(argumentFlags.outputFileName);

        //call function stubs based on arguments
        if (argumentFlags.lexing) lexing(argumentFlags, inputHandler, outputHandler);
        if (argumentFlags.semantics) semantics();
        if (argumentFlags.printSemanticInformation) printSemanticInformation();
        if (argumentFlags.compile) compile();
    }

    static void lexing(ArgumentFlags argumentFlags, InputHandler inputHandler, OutputHandler outputHandler) {
        System.out.println("Lexing");
        KxiLexer kxiLexer = new KxiLexer(inputHandler.fileToCharStream());

        if (argumentFlags.printTokens) printTokens(kxiLexer);
        if (argumentFlags.parseTree) parseTree(new CommonTokenStream(kxiLexer), argumentFlags, outputHandler);
    }

    static void printTokens(KxiLexer kxiLexer) {
        System.out.println("Printing Tokens");
        kxiLexer.printTokens();
    }

    static void parseTree(CommonTokenStream tokenStream, ArgumentFlags argumentFlags, OutputHandler outputHandler) {
        KxiParser parser = new KxiParser(tokenStream);
        System.out.println("Parsing");
        AntlrToKxiVisitor antlrToKxiVisitor = new AntlrToKxiVisitor();
        antlrToKxiVisitor.visitCompilationUnit(parser.compilationUnit());
        KxiMain kxiMain = (KxiMain) antlrToKxiVisitor.getRootNode();

        if(argumentFlags.printASTDiagram) {
            try {
                printASTDiagram(kxiMain, outputHandler);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void printASTDiagram(KxiMain kxiMain, OutputHandler outputHandler) throws IOException {
        System.out.println("Creating an AST diagram");
        GraphVizVisitor graphVizVisitor = new GraphVizVisitor(kxiMain);
        outputHandler.outputAST(graphVizVisitor.getGraph());
    }

    static void semantics() {
        System.out.println("Checking semantic validity");
    }

    static void printSemanticInformation() {
        System.out.println("Emitting semantic information");
    }

    static void compile() {
        System.out.println("Compiling to assembly");
    }

}
