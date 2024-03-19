package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.KxiType;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public
class KxiNewExpressionIndex extends AbstractKxiExpression {
    private AbstractKxiExpression index;
    private KxiType type;

    public KxiNewExpressionIndex(AbstractKxiExpression index, KxiType type) {
        super(index, type);
        this.index = index;
        this.type = type;
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
