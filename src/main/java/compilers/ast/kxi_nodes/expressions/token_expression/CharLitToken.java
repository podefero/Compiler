package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.ScalarType;

public class CharLitToken extends TokenType<Character> {


    public CharLitToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.CHAR;
        value = new EncodeCharacters().encodeText(tokenText).charAt(0);
    }

    @Override
    public Character getTokenText() {
        return value;
    }
}
