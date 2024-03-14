package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;

public class BoolToken extends TokenType<Boolean> {


    public BoolToken(String tokenText, ScalarType scalarType) {
        super(tokenText, scalarType);
        if(tokenText.equals("false")) value = false;
        else if(tokenText.equals("true")) value = true;
    }

    @Override
    public Boolean getTokenText() {
        return value;
    }
}
