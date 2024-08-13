package compilers.ast.kxi_nodes.expressions.literals;

import compilers.ast.kxi_nodes.token_literals.StringLitToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExpressionStringLit extends ExpressionLiteral<StringLitToken> {
    ExpressionIdLit globalVariable;
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
