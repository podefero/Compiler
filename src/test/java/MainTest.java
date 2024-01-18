import compilers.Main;
import compilers.commandargs.ArgumentFlags;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testAllArgs() {
        String[] allArgs = {"-l", "-p", "-s", "-c", "-o tokens.lex out.asm AST.dot", "-i input.kxi"};
        ArgumentFlags argumentFlags = new ArgumentFlags(allArgs);
        assertFalse(argumentFlags.hasError);
    }

    @Test
    void outputArgNoFile() {
        String[] arg = {"-o"};
        ArgumentFlags argumentFlags = new ArgumentFlags(arg);
        assertFalse(argumentFlags.hasError);
    }

    @Test
    void invalidArg() {
        String[] invalidArg = {"-z"};
        ArgumentFlags argumentFlags = new ArgumentFlags(invalidArg);
        assertEquals("unrecognized arguments: '-z'", argumentFlags.errorMessage);
    }
}