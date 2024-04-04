package compilers.ast.kxi_nodes.expressions.binary.arithmic;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.visitor.kxi.KxiVisitorBase;

public abstract class AbstractBinaryArithmicExpression extends AbstractKxiBinaryOperation {

    public AbstractBinaryArithmicExpression(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
       super.accept(visit);
    }
}
