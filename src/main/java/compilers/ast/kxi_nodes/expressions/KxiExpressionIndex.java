package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.KxiIndex;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public
class KxiExpressionIndex extends AbstractKxiExpression {
    private KxiIndex index;
    private AbstractKxiExpression expression;

    public KxiExpressionIndex(KxiIndex index, AbstractKxiExpression expression) {
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
