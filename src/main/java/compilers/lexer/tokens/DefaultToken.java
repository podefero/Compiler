package compilers.lexer.tokens;

public class DefaultToken extends TokenType<String> {
    private String value;

    public DefaultToken(String tokenText) {
        super(tokenText);
        value = tokenText;
    }


    @Override
    public String getValue() {
        return value;
    }
}
