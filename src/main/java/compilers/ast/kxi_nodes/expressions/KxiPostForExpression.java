package compilers.ast.kxi_nodes.expressions;

import compilers.ast.intermediate.expression.InterExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KxiPostForExpression extends AbstractKxiExpression {
    private AbstractKxiExpression expression;
    private List<InterExpression> interExpressions;

    public KxiPostForExpression(AbstractKxiExpression expression) {
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
