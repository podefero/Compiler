package compilers.ast.kxi_nodes.expressions.binary.arithmic;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiSubtract extends AbstractBinaryArithmicExpression {
    public KxiSubtract(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
