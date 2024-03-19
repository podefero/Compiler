package compilers.ast.kxi_nodes.expressions.binary.arithmic;

import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.VisitKxi;

public abstract class AbstractBinaryArithmicExpression extends AbstractKxiBinaryOperation {

    public AbstractBinaryArithmicExpression(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
    }

    @Override
    public void accept(VisitKxi visit) {
       super.accept(visit);
    }
}
