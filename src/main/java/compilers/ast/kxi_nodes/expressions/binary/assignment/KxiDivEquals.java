package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiDivEquals extends AbstractBinaryAssignmentExpression{

    public KxiDivEquals(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
