package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;

public class NullToken extends TokenType<String> {

    public NullToken(String tokenText, ScalarType scalarType) {
        super(tokenText, scalarType);
        value = tokenText;
    }


    @Override
    public String getTokenText() {
        return value;
    }
}
