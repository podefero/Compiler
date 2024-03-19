package compilers.ast.kxi_nodes.expressions;

import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class KxiExpressionArguments extends AbstractKxiExpression{
    private List<AbstractKxiExpression> arguments;
    private AbstractKxiExpression methodExpression;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        methodExpression.accept(visit);
        visitList(arguments, visit);
    }
}
