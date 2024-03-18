package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.ScalarType;

public class IntLitToken extends TokenType<Integer> {

    public IntLitToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.INT;
        value = Integer.parseInt(tokenText);
    }


    @Override
    public Integer getTokenText() {
        return value;
    }
}
