package compilers.lexer.tokens;

public class IdentifierToken extends TokenType<String> {
    private String identifier;

    public IdentifierToken(String tokenText) {
        super(tokenText);
        identifier = tokenText;
    }


    @Override
    public String getValue() {
        return identifier;
    }
}
