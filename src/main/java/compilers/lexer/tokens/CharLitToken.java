package compilers.lexer.tokens;

public class CharLitToken extends TokenType<Character> {
    private Character value;

    public CharLitToken(String tokenText) {
        super(tokenText);
        value = new EncodeCharacters().encodeText(tokenText, false).charAt(0);
    }


    @Override
    public Character getValue() {
        return value;
    }
}
