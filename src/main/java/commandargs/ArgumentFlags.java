package commandargs;

import exceptions.ArgumentException;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.*;

public final class ArgumentFlags {
    public static boolean lexing;
    public static boolean printTokens;
    public static boolean parseTree;
    public static boolean printASTDiagram;
    public static boolean semantics;
    public static boolean printSemanticInformation;
    public static boolean compile;
    public static boolean graphicalAST;
    public static boolean outputFile;
    public static String outputFileName;
    public static boolean inputFile;
    public static String inputFileName;

    public static void init(String[] args) {
        //setup parser
        ArgumentParser parser = ArgumentParsers.newFor("prog").build()
                .description("Handle compiler arguments");

        //add arguments
        parser.addArgument("-l")
                .action(ArgumentFlags.setFlag())
                .help("lex the input and print the token stream");
        parser.addArgument("-p")
                .action(ArgumentFlags.setFlag())
                .help("create an abstract syntax tree and print any syntax errors that arise");
        parser.addArgument("-s")
                .action(ArgumentFlags.setFlag())
                .help("check the program for semantic correctness and display any errors that arise");
        parser.addArgument("-c")
                .action(ArgumentFlags.setFlag())
                .help("compile the program to assembly ");
        parser.addArgument("-a")
                .action(ArgumentFlags.setFlag())
                .help("create an abstract syntax tree and produce a graphical representation of it");

        //special case, not handled in GenericArgumentAction
        //handled below
        parser.addArgument("-o")
                .dest("outputFile")
                .metavar("FILE")
                .type(String.class)
                .nargs("*")
                .help("<output-prefix>: specify the beginning of the file(s) to be used for output; if not specified, output should go to STDOUT.");

        parser.addArgument("-i")
                .dest("inputFile")
                .metavar("KXI")
                .type(String.class)
                .nargs("*")
                .help("<filename>: specify the filename of the KXI program to be compiled; if not specified, input should come from STDIN.");

        try {
            Namespace res = parser.parseArgs(args);
            //handle empty args
            if (args.length == 0) parser.printHelp();

            //handle file output
            String outputFile = res.getString("outputFile");
            if (outputFile != null) {
                ArgumentFlags.outputFile = true;
                ArgumentFlags.outputFileName = outputFile;
            }

            //handle file input
            String inputFile = res.getString("inputFile");
            if (inputFile != null) {
                ArgumentFlags.inputFile = true;
                ArgumentFlags.inputFileName = inputFile;
            }

        } catch (ArgumentParserException e) {
            parser.handleError(e);
            throw new ArgumentException(e.getMessage(), e.getCause());
        }

    }

    public static ArgumentAction setFlag() {
        return new GenericArgumentAction();
    }

}
