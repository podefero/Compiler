package compilers.ast.kxi_nodes.expressions.binary.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.visitor.kxi.VisitKxi;

public abstract class AbstractBinaryConditionalExpression extends AbstractKxiBinaryOperation {
    public AbstractBinaryConditionalExpression(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
    }

    @Override
    public void accept(VisitKxi visit) {
        super.accept(visit);
    }
}
