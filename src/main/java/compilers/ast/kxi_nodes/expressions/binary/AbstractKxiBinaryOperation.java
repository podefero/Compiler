package compilers.ast.kxi_nodes.expressions.binary;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractKxiBinaryOperation extends AbstractKxiExpression {
    protected AbstractKxiExpression expressionL;
    protected AbstractKxiExpression expressionR;

    @Override
    public void accept(VisitKxi visit) {
        visitChildren(visit);
    }

    @Override
    public void visitChildren(VisitKxi visitKxi) {
        expressionL.accept(visitKxi);
        expressionR.accept(visitKxi);
    }
}
