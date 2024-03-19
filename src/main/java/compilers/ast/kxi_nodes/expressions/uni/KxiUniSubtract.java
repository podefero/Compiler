package compilers.ast.kxi_nodes.expressions.uni;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.VisitKxi;

public class KxiUniSubtract extends AbstractKxiUniOperation{
    public KxiUniSubtract(AbstractKxiExpression expression) {
        super(expression);
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        super.accept(visit);
        visit.visit(this);
    }
}
