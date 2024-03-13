package compilers.ast.kxi_nodes.expressions.binary.arithmic;

import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public abstract class AbstractBinaryArithmicExpression extends AbstractKxiBinaryOperation {

    public AbstractBinaryArithmicExpression(AbstractKxiExpression expressionL, AbstractKxiExpression expressionR) {
        super(expressionL, expressionR);
    }
}
