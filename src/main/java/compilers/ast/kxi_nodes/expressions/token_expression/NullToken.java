package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;

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

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visit.visit(this);
    }
}
