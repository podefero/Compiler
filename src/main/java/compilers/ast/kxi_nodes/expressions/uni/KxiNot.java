package compilers.ast.kxi_nodes.expressions.uni;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.VisitKxi;

public class KxiNot extends AbstractKxiUniOperation{
    public KxiNot(AbstractKxiExpression expression) {
        super(expression);
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        super.accept(visit);
        visit.visit(this);
    }
}
