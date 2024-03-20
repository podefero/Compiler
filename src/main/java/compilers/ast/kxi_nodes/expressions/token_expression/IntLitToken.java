package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;

public class IntLitToken extends TokenType<Integer> {

    public IntLitToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.INT;
        value = Integer.parseInt(tokenText);
    }


    @Override
    public Integer getTokenText() {
        return value;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visit.visit(this);
    }
}
