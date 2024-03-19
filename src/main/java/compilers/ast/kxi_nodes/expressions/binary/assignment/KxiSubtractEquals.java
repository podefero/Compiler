package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.VisitKxi;

public class KxiSubtractEquals extends AbstractBinaryAssignmentExpression{

    public KxiSubtractEquals(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        super.accept(visit);
        visit.visit(this);
    }
}
