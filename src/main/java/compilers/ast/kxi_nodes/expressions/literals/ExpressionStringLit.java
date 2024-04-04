package compilers.ast.kxi_nodes.expressions.literals;

import compilers.ast.kxi_nodes.token_literals.StringLitToken;
import compilers.visitor.kxi.KxiVisitorBase;

public class ExpressionStringLit extends ExpressionLiteral<StringLitToken> {
    public ExpressionStringLit(StringLitToken tokenLiteral) {
        super(tokenLiteral);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
