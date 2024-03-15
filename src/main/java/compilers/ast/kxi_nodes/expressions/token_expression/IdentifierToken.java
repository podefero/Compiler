package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;

public class IdentifierToken extends TokenType<String> {

    public IdentifierToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.ID;
        value = tokenText;
    }

    @Override
    public String getTokenText() {
        return value;
    }
}
