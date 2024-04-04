package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;

public class KxiIndex extends AbstractKxiNode {
    private AbstractKxiExpression index;

    public KxiIndex(AbstractKxiExpression index) {
        super(index);
        this.index = index;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
