package compilers.commandargs;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class ArgumentFlags {
    public String errorMessage;
    public boolean hasError;
    public boolean lexing;
    public boolean printTokens;
    public boolean parseTree;
    public boolean printASTDiagram;
    public boolean semantics;
    public boolean printSemanticInformation;
    public boolean compile;
    public boolean graphicalAST;
    public boolean hasOutputFile;
    public String outputFileName;
    public boolean hasInputFile;
    public String inputFileName;

    public ArgumentFlags(String[] args) {
        init(args);
    }

    public void init(String[] args) {
        //setup parser
        ArgumentParser parser = ArgumentParsers.newFor("prog").build()
                .description("Handle compiler arguments");

        parser.addArgument("-l")
                .action(setFlag())
                .help("lex the input and print the token stream");
        parser.addArgument("-p")
                .action(setFlag())
                .help("create an abstract syntax tree and print any syntax errors that arise");
        parser.addArgument("-s")
                .action(setFlag())
                .help("check the program for semantic correctness and display any errors that arise");
        parser.addArgument("-c")
                .action(setFlag())
                .help("compile the program to assembly ");

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
                hasOutputFile = true;
                outputFileName = outputFile;
            }

            //handle file input
            String inputFile = res.getString("inputFile");
            if (inputFile != null) {
                hasInputFile = true;
                inputFileName = inputFile;
            }

        } catch (ArgumentParserException e) {
            parser.handleError(e);
            errorMessage = e.getMessage();
        }

    }

    public ArgumentAction setFlag() {
        return new GenericArgumentAction(this);
    }

}
