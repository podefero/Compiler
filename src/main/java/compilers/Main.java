package compilers;

import compilers.commandargs.ArgumentFlags;

public class Main {
    public static void main(String[] args) {
        //parse the command line args
        ArgumentFlags argumentFlags = new ArgumentFlags(args);

        //call function stubs based on arguments
        if (argumentFlags.lexing) lexing();
        if (argumentFlags.printTokens) printTokens();
        if (argumentFlags.parseTree) parseTree();
        if (argumentFlags.printASTDiagram) printASTDiagram();
        if (argumentFlags.semantics) semantics();
        if (argumentFlags.printSemanticInformation) printSemanticInformation();
        if (argumentFlags.compile) compile();
        if (argumentFlags.graphicalAST) printGraphicalAST();
        if (argumentFlags.hasOutputFile) outputFiles(argumentFlags.outputFileName);
        if (argumentFlags.hasInputFile) inputFile(argumentFlags.inputFileName);
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

    static void outputFiles(String files) {
        if (files.equals("[]")) {
            System.out.println("using STDOUT to print");
        } else {
            System.out.println("Printing files " + files);
        }
    }

    static void inputFile(String file) {
        if (file.equals("[]")) {
            System.out.println("using STDIN to input");
        } else {
            System.out.println("Inputting file " + file);
        }
    }

}
