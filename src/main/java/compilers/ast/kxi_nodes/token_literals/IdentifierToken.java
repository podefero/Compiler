package compilers.ast.kxi_nodes.token_literals;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;

public class IdentifierToken extends TokenLiteral<String> {

    public IdentifierToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.ID;
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
