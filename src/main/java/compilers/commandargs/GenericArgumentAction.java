package compilers.commandargs;

import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.util.Map;

public class GenericArgumentAction implements ArgumentAction {
    @Override
    public void run(ArgumentParser argumentParser, Argument argument, Map<String, Object> map, String s, Object o) throws ArgumentParserException {
        switch (s) {
            case "-l":
                ArgumentFlags.lexing = true;
                ArgumentFlags.printTokens = true;
                break;
            case "-p":
                ArgumentFlags.parseTree = true;
                ArgumentFlags.lexing = true;
                ArgumentFlags.printASTDiagram = true;
                break;
            case "-s":
                ArgumentFlags.semantics = true;
                ArgumentFlags.parseTree = true;
                ArgumentFlags.lexing = true;
                ArgumentFlags.printSemanticInformation = true;
                break;
            case "-c":
                ArgumentFlags.compile = true;
                ArgumentFlags.semantics = true;
                ArgumentFlags.parseTree = true;
                ArgumentFlags.lexing = true;
                break;
            case "-a":
                ArgumentFlags.graphicalAST = true;
                ArgumentFlags.lexing = true;
                ArgumentFlags.parseTree = true;
                break;
        }

    }

    @Override
    public void onAttach(Argument argument) {

    }

    @Override
    public boolean consumeArgument() {
        return false;
    }
}
