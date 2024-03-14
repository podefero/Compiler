package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;

public class DefaultToken extends TokenType<String> {

    public DefaultToken(String tokenText, ScalarType scalarType) {
        super(tokenText, scalarType);
        value = tokenText;
    }

    @Override
    public String getTokenText() {
        return value;
    }
}
