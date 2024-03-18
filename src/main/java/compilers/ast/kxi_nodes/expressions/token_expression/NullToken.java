package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.ScalarType;

public class NullToken extends TokenType<String> {

    public NullToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.NULL;
        value = tokenText;
    }


    @Override
    public String getTokenText() {
        return value;
    }
}
