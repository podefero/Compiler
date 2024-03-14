package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;

public class StringLitToken extends TokenType<String> {
    public StringLitToken(String tokenText, ScalarType scalarType) {
        super(tokenText, scalarType);
        value = tokenText;
    }


    @Override
    public String getTokenText() {
        EncodeCharacters encodeCharacters = new EncodeCharacters();
        return encodeCharacters.encodeText(value);
    }



}
