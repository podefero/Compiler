package compilers.ast.kxi_nodes.expressions;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public
class KxiExpressionIndex extends AbstractKxiExpression {
    private AbstractKxiExpression index;
    private AbstractKxiExpression expression;

    public KxiExpressionIndex(AbstractKxiExpression index, AbstractKxiExpression expression) {
        super(index, expression);
        this.index = index;
        this.expression = expression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
