package compilers.ast.kxi_nodes.expressions.token_expression;

public class CharLitToken extends TokenType<Character> {
    private Character value;

    public CharLitToken(String tokenText) {
        super(tokenText);
        value = new EncodeCharacters().encodeText(tokenText).charAt(0);
    }


    @Override
    public Character getValue() {
        return value;
    }
}
