package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.VisitKxi;

public class ThisToken extends TokenType<String> {

    public ThisToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.THIS;
        value = tokenText;
    }

    @Override
    public String getTokenText() {
        return value;
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visit.visit(this);
    }
}
