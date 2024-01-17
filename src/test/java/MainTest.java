import compilers.Main;
import compilers.commandargs.ArgumentFlags;
import net.sourceforge.argparse4j.annotation.Arg;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testAllArgs() {
        String[] allArgs = {"-l", "-p", "-s", "-c", "-o tokens.lex out.asm AST.dot", "-a", "-i input.kxi"};
        assertDoesNotThrow(() -> Main.main(allArgs));
    }

    @Test
    void outputArgNoFile() {
        String[] arg = {"-o"};
        assertDoesNotThrow(() -> Main.main(arg));
    }

    @Test
    void invalidArg() {
        String[] invalidArg = {"-z"};
        Main.main(invalidArg);
        assertEquals("unrecognized arguments: '-z'", ArgumentFlags.errorMessage);
    }
}