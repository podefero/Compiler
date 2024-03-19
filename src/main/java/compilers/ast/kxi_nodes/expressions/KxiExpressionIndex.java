package compilers.ast.kxi_nodes.expressions;

import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiExpressionIndex extends AbstractKxiExpression{
    private AbstractKxiExpression index;
    private AbstractKxiExpression expression;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        expression.accept(visit);
        index.accept(visit);
    }
}
