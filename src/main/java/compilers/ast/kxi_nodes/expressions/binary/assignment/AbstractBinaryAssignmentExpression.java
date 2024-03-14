package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;


public abstract class AbstractBinaryAssignmentExpression extends AbstractKxiBinaryOperation {


    public AbstractBinaryAssignmentExpression(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionL, expressionR);
    }
}
