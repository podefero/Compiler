package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

public class KxiDotExpression extends AbstractKxiExpression{
    private AbstractKxiExpression expression;
    private IdentifierToken id;

    public KxiDotExpression(AbstractKxiExpression expression, IdentifierToken id) {
        super(expression, id);
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        id.accept(visit);
        expression.accept(visit);
    }
}
