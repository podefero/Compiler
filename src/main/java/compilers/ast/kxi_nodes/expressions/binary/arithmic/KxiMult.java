package compilers.ast.kxi_nodes.expressions.binary.arithmic;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiMult extends AbstractBinaryArithmicExpression{
    public KxiMult(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
