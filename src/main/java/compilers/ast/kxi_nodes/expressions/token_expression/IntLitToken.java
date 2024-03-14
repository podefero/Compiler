package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;

public class IntLitToken extends TokenType<Integer> {

    public IntLitToken(String tokenText, ScalarType scalarType) {
        super(tokenText, scalarType);
        value = Integer.parseInt(tokenText);
    }


    @Override
    public Integer getTokenText() {
        return value;
    }
}
