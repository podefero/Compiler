package compilers.ast.kxi_nodes.expressions;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiPreForExpression extends AbstractKxiExpression {
    private AbstractKxiExpression expression;

    public KxiPreForExpression(AbstractKxiExpression expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
