package compilers.ast.kxi_nodes.expressions.binary.arithmic;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiDiv extends AbstractBinaryArithmicExpression{
    public KxiDiv(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
