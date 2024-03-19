package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;

import java.util.Optional;

public class KxiIfStatement extends AbstractKxiConditionalStatement {
    private Optional<AbstractKxiStatement> elseStatement;


    public KxiIfStatement(Optional<AbstractKxiStatement> elseStatement, AbstractKxiStatement statement, AbstractKxiExpression conditionalExpression) {
        super(statement, conditionalExpression);
        this.elseStatement = elseStatement;
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
        visitNode(elseStatement, visit);
    }
}
