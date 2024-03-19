package compilers.ast.kxi_nodes.expressions;

import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class KxiParenthExpression extends AbstractKxiExpression {
    private AbstractKxiExpression expression;

    public KxiParenthExpression(AbstractKxiExpression expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
