package compilers.lexer.tokens;

public class BoolToken extends TokenType<Boolean> {
    private boolean value;

    public BoolToken(String tokenText) {
        super(tokenText);
        if(tokenText.equals("false")) value = false;
        else if(tokenText.equals("true")) value = true;
    }


    @Override
    public Boolean getValue() {
        return value;
    }
}
