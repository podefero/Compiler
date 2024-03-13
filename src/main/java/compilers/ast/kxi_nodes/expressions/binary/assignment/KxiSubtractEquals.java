package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiSubtractEquals extends AbstractBinaryAssignmentExpression{

    public KxiSubtractEquals(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
