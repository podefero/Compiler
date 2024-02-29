package compilers.lexer.tokens;

public abstract class TokenType<T> {
    private T value;
    public TokenType(String tokenText) {

    }

    public T getValue() {
        return value;
    }
}
