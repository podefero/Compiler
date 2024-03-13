package compilers.ast.kxi_nodes.expressions.binary.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiGreaterEqualsThen extends AbstractBinaryConditionalExpression{
    public KxiGreaterEqualsThen(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
