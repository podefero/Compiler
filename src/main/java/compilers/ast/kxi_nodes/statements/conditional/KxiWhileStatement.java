package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.binary.conditional.AbstractBinaryConditionalExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;

public class KxiWhileStatement extends AbstractKxiConditionalStatement{

    public KxiWhileStatement(AbstractKxiStatement statement, AbstractKxiExpression conditionalExpression) {
        super(statement, conditionalExpression);
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        conditionalExpression.accept(visit);
        statement.accept(visit);
    }
}
