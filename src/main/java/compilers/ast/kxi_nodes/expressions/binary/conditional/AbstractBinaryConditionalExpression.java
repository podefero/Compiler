package compilers.ast.kxi_nodes.expressions.binary.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;

public abstract class AbstractBinaryConditionalExpression extends AbstractKxiBinaryOperation {
    public AbstractBinaryConditionalExpression(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
