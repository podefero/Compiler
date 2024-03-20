package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;

public class KxiMultEquals extends AbstractBinaryAssignmentExpression {

    public KxiMultEquals(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        super.accept(visit);
        visit.visit(this);
    }
}
