package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiSwitchStatement extends AbstractKxiStatement{
    private KxiCaseBlock caseBlock;
    private AbstractKxiExpression expression;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        expression.accept(visit);
        caseBlock.accept(visit);
    }
}
