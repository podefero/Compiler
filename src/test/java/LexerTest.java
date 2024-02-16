import compilers.lexer.KxiLexer;
import compilers.lexer.TokenProcessor;
import compilers.lexer.tokens.TokenType;
import compilers.util.InputStreamToChar;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LexerTest {

    private boolean debug = false;

    @Test
    void validKxiFileTest() {
        //This is testing if a valid kxi file
        //fails on unknown tokens
        String filepath = getClass().getResource("StressTest.kxi").getPath();
        CharStream charStream;

        charStream = new InputStreamToChar().FileToCharStream(filepath);

        // Create an instance of the ANTLR-generated lexer
        KxiLexer kxiLexer = new KxiLexer(charStream);

        // Create a CommonTokenStream to get tokens from the lexer
        CommonTokenStream tokens = new CommonTokenStream(kxiLexer);

        // Iterate through the tokens and perform testing actions
        tokens.fill();
        List<Token> tokenList = tokens.getTokens();
        for (Token token : tokenList) {
            String tokenName = kxiLexer.getVocabulary().getSymbolicName(token.getType());
            assertEquals(false, tokenName.equals("UNKNOWN"));
            //System.out.println("Line#: " + token.getLine() + ":" + token.getCharPositionInLine() + " " + token.getText() + " " + tokenName);
        }
    }

    @Test
    void testTokenIntLit() {
        testTokenType("100", KxiLexer.INTLIT, 100);
    }

    @Test
    void testTokenStringLit() {
        testTokenType("\"John Doe\"", KxiLexer.STRINGLIT, "John Doe");
    }

    @Test
    void testTokenSingleQuoteLit() {
        testTokenType("\"'JohnDoe'\"", KxiLexer.STRINGLIT, "'JohnDoe'");
    }

    @Test
    void testTokenCharLit() {
        testTokenType("'J'", KxiLexer.CHARLIT, 'J');
    }

    @Test
    void testTokenBool() {
        testTokenType("true", KxiLexer.TRUE, true);
        testTokenType("false", KxiLexer.FALSE, false);
    }

    @Test
    void testTokenDefault() {
        testTokenType("Chicken", KxiLexer.IDENTIFIER, "Chicken");
    }

    @Test
    void testTokenEscapeSequences() {
        testTokenType("\"John\\nDoe\"", KxiLexer.STRINGLIT, "John\nDoe");
        testTokenType("\"John\\rDoe\"", KxiLexer.STRINGLIT, "John\rDoe");
        testTokenType("\"John\\tDoe\"", KxiLexer.STRINGLIT, "John\tDoe");
        testTokenType("\"John\\n\\rDoe\"", KxiLexer.STRINGLIT, "John\n\rDoe");
        testTokenType("\"John\\\"Doe\"", KxiLexer.STRINGLIT, "John\"Doe");
    }

    @Test
    void testInvalidTokens() {

    }

    private List<Token> getTokenList(String input) {
        CharStream charStream = CharStreams.fromString(input);
        KxiLexer kxiLexer = new KxiLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(kxiLexer);
        tokens.fill();
        List<Token> tokenList = tokens.getTokens();
        if (debug) {
            printTokens(tokenList, kxiLexer);
        }
        return tokenList;
    }

    void testTokenType(String input, int tokenType, Object expected) {
        //make sure I get the right data type out of each token
        List<Token> tokens = getTokenList(input);
        TokenProcessor processor = new TokenProcessor();
        TokenType processedType = null;

        for (Token token : tokens) {
            if (token.getType() == tokenType) {
                processedType = processor.getTokenType(token);
            }
        }
        if (processedType != null)
            assertEquals(expected, processedType.getValue());
        else {
            printTokens(tokens);
            fail("processedType is null");
        }
    }

    private void printTokens(List<Token> tokens) {
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    private void printTokens(List<Token> tokens, KxiLexer kxiLexer) {
        for (Token token : tokens) {
            String tokenName = kxiLexer.getVocabulary().getSymbolicName(token.getType());
            System.out.println("Line#: " + token.getLine() + ":" + token.getCharPositionInLine() + " " + token.getText() + " " + tokenName);
        }
    }

}
