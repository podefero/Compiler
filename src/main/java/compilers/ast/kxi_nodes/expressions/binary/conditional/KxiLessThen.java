package compilers.ast.kxi_nodes.expressions.binary.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiLessThen extends AbstractBinaryConditionalExpression{
    public KxiLessThen(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
