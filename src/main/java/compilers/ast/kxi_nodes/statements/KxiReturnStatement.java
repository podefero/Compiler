package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class KxiReturnStatement extends AbstractKxiStatement{
    private Optional<AbstractKxiExpression> expression;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        visitNode(expression, visit);
    }

}
