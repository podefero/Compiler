package compilers.ast.kxi_nodes.expressions.binary.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiNotEquals extends AbstractBinaryConditionalExpression{
    public KxiNotEquals(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
