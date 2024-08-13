package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.helper.KxiFordSemi;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiForWhileExpression extends AbstractKxiExpression {
//    private AbstractKxiExpression expression;
//    private AbstractKxiExpression postExpression;
//    private AbstractKxiExpression preExpression;

    public KxiForWhileExpression(KxiPostForExpression postExpression, AbstractKxiExpression conditionalExpression, AbstractKxiExpression preExpression) {
        super(postExpression, conditionalExpression, preExpression);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
