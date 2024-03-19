package compilers.ast.kxi_nodes.expressions.binary;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.VisitKxi;
import lombok.Getter;

@Getter
public abstract class AbstractKxiBinaryOperation extends AbstractKxiExpression {
    protected AbstractKxiExpression expressionR;
    protected AbstractKxiExpression expressionL;

    public AbstractKxiBinaryOperation(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
        this.expressionR = expressionR;
        this.expressionL = expressionL;
    }

    @Override
    public void accept(VisitKxi visit) {
        visitChildren(visit);
    }
}
