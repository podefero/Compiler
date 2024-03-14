package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;

public class IdentifierToken extends TokenType<String> {

    public IdentifierToken(String tokenText, ScalarType scalarType) {
        super(tokenText, scalarType);
        value = tokenText;
    }

    @Override
    public String getTokenText() {
        return value;
    }
}
