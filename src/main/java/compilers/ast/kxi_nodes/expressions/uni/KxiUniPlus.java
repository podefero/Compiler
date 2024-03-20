package compilers.ast.kxi_nodes.expressions.uni;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;

public class KxiUniPlus extends AbstractKxiUniOperation{
    public KxiUniPlus(AbstractKxiExpression expression) {
        super(expression);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        super.accept(visit);
        visit.visit(this);
    }
}
