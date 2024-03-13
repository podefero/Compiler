package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiPlusEquals extends AbstractBinaryAssignmentExpression{

    public KxiPlusEquals(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
