package compilers.ast.kxi_nodes.expressions.binary.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiLessEqualsThen extends AbstractBinaryConditionalExpression{
    public KxiLessEqualsThen(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
