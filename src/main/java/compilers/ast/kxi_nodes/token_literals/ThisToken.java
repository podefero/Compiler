package compilers.ast.kxi_nodes.token_literals;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;

public class ThisToken extends TokenLiteral<String> {

    public ThisToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.THIS;
        value = tokenText;
    }

    @Override
    public String getTokenText() {
        return value;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visit.visit(this);
    }
}
