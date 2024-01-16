import exceptions.ArgumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testAllArgs() {
        String[] allArgs = {"-l", "-p", "-s", "-c", "-o tokens.lex out.asm AST.dot", "-a", "-i input.kxi"};
        assertDoesNotThrow(() -> Main.main(allArgs));
    }

    @Test
    void invalidArg() {
        String[] invalidArg = {"-z"};
        assertThrows(ArgumentException.class, () -> Main.main(invalidArg));
    }
}