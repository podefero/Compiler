package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;

public class CharLitToken extends TokenType<Character> {


    public CharLitToken(String tokenText, ScalarType scalarType) {
        super(tokenText, scalarType);
        value = new EncodeCharacters().encodeText(tokenText).charAt(0);
    }

    @Override
    public Character getTokenText() {
        return value;
    }
}
