package compilers;

import compilers.commandargs.ArgumentFlags;

public class Main {
    public static void main(String[] args) {
        //parse the command line args
        ArgumentFlags.init(args);

        //call function stubs based on arguments
        if (ArgumentFlags.lexing) lexing();
        if (ArgumentFlags.printTokens) printTokens();
        if (ArgumentFlags.parseTree) parseTree();
        if (ArgumentFlags.printASTDiagram) printASTDiagram();
        if (ArgumentFlags.semantics) semantics();
        if (ArgumentFlags.printSemanticInformation) printSemanticInformation();
        if (ArgumentFlags.compile) compile();
        if (ArgumentFlags.graphicalAST) printGraphicalAST();
        if (ArgumentFlags.outputFile) outputFiles();
        if (ArgumentFlags.inputFile) inputFile();
    }

    static void lexing() {
        System.out.println("Lexing");
    }

    static void printTokens() {
        System.out.println("Printing Tokens");
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

    static void outputFiles() {
        if (ArgumentFlags.outputFileName.equals("[]")) {
            System.out.println("using STDOUT to print");
        } else {
            System.out.println("Printing files " + ArgumentFlags.outputFileName);
        }
    }

    static void inputFile() {
        if (ArgumentFlags.inputFileName.equals("[]")) {
            System.out.println("using STDIN to input");
        } else {
            System.out.println("Inputting file " + ArgumentFlags.inputFileName);
        }
    }

}
