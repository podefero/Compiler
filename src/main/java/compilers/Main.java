package compilers;

import compilers.commandargs.ArgumentFlags;
import compilers.lexer.KxiLexer;
import compilers.util.InputHandler;

public class Main {
    public static void main(String[] args) {
        //parse the command line args
        ArgumentFlags argumentFlags = new ArgumentFlags(args);
        InputHandler inputHandler = new InputHandler(argumentFlags.inputFileName);

        //call function stubs based on arguments
        if (argumentFlags.hasOutputFile) outputFiles(argumentFlags.outputFileName);
        if (argumentFlags.lexing) lexing(argumentFlags, inputHandler);
        if (argumentFlags.parseTree) parseTree();
        if (argumentFlags.printASTDiagram) printASTDiagram();
        if (argumentFlags.semantics) semantics();
        if (argumentFlags.printSemanticInformation) printSemanticInformation();
        if (argumentFlags.compile) compile();
        if (argumentFlags.graphicalAST) printGraphicalAST();
    }

    static void lexing(ArgumentFlags argumentFlags, InputHandler inputHandler) {
        System.out.println("Lexing");
        KxiLexer kxiLexer = new KxiLexer(inputHandler.fileToCharStream());

        if (argumentFlags.printTokens) printTokens(kxiLexer);
    }

    static void printTokens(KxiLexer kxiLexer) {
        System.out.println("Printing Tokens");
        kxiLexer.printTokens();
    }

    static void parseTree() {
        System.out.println("Parsing");
    }

    static void printASTDiagram() {
        System.out.println("Creating an AST diagram");
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

    static void printGraphicalAST() {
        System.out.println("Outputting graphical AST");
    }

    static void outputFiles(String files) {
        if (files.equals("[]")) {
            System.out.println("using STDOUT to print");
        } else {
            System.out.println("Printing files " + files);
        }
    }
}
