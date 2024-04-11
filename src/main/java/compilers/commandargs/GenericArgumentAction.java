package compilers.commandargs;

import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentAction;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.util.Map;

public class GenericArgumentAction implements ArgumentAction {
    private ArgumentFlags argumentFlags;

    public GenericArgumentAction(ArgumentFlags argumentFlags) {
        this.argumentFlags = argumentFlags;
    }

    @Override
    public void run(ArgumentParser argumentParser, Argument argument, Map<String, Object> map, String s, Object o) throws ArgumentParserException {
        switch (s) {
            case "-l":
                argumentFlags.lexing = true;
                argumentFlags.printTokens = true;
                break;
            case "-p":
                argumentFlags.parseTree = true;
                argumentFlags.lexing = true;
                argumentFlags.printASTDiagram = true;
                break;
            case "-s":
                argumentFlags.semantics = true;
                argumentFlags.parseTree = true;
                argumentFlags.lexing = true;
                break;
            case "-c":
                argumentFlags.compile = true;
                argumentFlags.semantics = true;
                argumentFlags.parseTree = true;
                argumentFlags.lexing = true;
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
