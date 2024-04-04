package compilers.ast.kxi_nodes.expressions.literals;

import compilers.ast.kxi_nodes.token_literals.IntLitToken;
import compilers.visitor.kxi.KxiVisitorBase;

public class ExpressionIntLit extends ExpressionLiteral<IntLitToken> {
    public ExpressionIntLit(IntLitToken tokenLiteral) {
        super(tokenLiteral);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
