package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiEquals extends AbstractBinaryAssignmentExpression {

    public KxiEquals(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
