package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;

public class KxiDotExpression extends AbstractKxiExpression{
    private AbstractKxiExpression expression;
    private IdentifierToken id;

    public KxiDotExpression(AbstractKxiExpression expression, IdentifierToken id) {
        super(expression, id);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(KxiVisitorBase visit) {
        id.accept(visit);
        expression.accept(visit);
    }
}
