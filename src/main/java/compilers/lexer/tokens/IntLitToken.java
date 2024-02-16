package compilers.lexer.tokens;

public class IntLitToken extends TokenType<Integer> {
    private int value;

    public IntLitToken(String tokenText) {
        super(tokenText);
        value = Integer.parseInt(tokenText);
    }


    @Override
    public Integer getValue() {
        return value;
    }
}
