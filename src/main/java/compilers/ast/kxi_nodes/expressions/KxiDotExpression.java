package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiDotExpression extends AbstractKxiExpression{
    private IdentifierToken id;
    private AbstractKxiExpression expression;


    public KxiDotExpression(IdentifierToken id, AbstractKxiExpression expression) {
        super(id, expression);
        this.id = id;
        this.expression = expression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
