package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;

public class BoolToken extends TokenType<Boolean> {


    public BoolToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.BOOL;
        if(tokenText.equals("false")) value = false;
        else if(tokenText.equals("true")) value = true;
    }

    @Override
    public Boolean getTokenText() {
        return value;
    }
}
