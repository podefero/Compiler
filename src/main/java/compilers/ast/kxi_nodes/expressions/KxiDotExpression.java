package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiDotExpression extends AbstractKxiExpression{
    private AbstractKxiExpression expression;
    private IdentifierToken id;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        id.accept(visit);
        expression.accept(visit);
    }
}
