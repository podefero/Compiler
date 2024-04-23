package compilers.ast.kxi_nodes.expressions.literals;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpressionIdLit extends ExpressionLiteral<IdentifierToken> {
    String id;
    ScalarType scalarType;
    public ExpressionIdLit(IdentifierToken tokenLiteral) {
        super(tokenLiteral);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
