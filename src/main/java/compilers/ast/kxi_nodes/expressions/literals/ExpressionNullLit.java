package compilers.ast.kxi_nodes.expressions.literals;

import compilers.ast.kxi_nodes.token_literals.NullToken;
import compilers.visitor.kxi.KxiVisitorBase;

public class ExpressionNullLit extends ExpressionLiteral<NullToken> {
    public ExpressionNullLit(NullToken tokenLiteral) {
        super(tokenLiteral);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
