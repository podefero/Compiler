package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.VisitKxi;


public abstract class AbstractBinaryAssignmentExpression extends AbstractKxiBinaryOperation {


    public AbstractBinaryAssignmentExpression(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionL, expressionR);
    }

    @Override
    public void accept(VisitKxi visit) {
        super.accept(visit);
    }
}
