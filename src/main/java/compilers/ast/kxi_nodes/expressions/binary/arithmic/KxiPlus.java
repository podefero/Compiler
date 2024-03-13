package compilers.ast.kxi_nodes.expressions.binary.arithmic;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiPlus extends AbstractBinaryArithmicExpression{
    public KxiPlus(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
