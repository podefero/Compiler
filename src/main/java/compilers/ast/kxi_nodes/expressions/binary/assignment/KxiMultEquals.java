package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiMultEquals extends AbstractBinaryAssignmentExpression {

    public KxiMultEquals(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
