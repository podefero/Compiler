package compilers.ast.kxi_nodes.expressions.literals;

import compilers.ast.kxi_nodes.token_literals.ThisToken;
import compilers.visitor.kxi.KxiVisitorBase;

public class ExpressionThisLit extends ExpressionLiteral<ThisToken> {
    public ExpressionThisLit(ThisToken tokenLiteral) {
        super(tokenLiteral);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
