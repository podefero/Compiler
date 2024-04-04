package compilers.ast.kxi_nodes.expressions.literals;

import compilers.ast.kxi_nodes.token_literals.CharLitToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class ExpressionCharLit extends ExpressionLiteral<CharLitToken> {


    public ExpressionCharLit(CharLitToken tokenLiteral) {
        super(tokenLiteral);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
