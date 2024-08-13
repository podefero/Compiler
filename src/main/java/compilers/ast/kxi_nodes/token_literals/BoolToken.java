package compilers.ast.kxi_nodes.token_literals;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;

public class BoolToken extends TokenLiteral<Boolean> {


    public BoolToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.BOOL;
        if (tokenText.equals("false")) value = false;
        else if (tokenText.equals("true")) value = true;
    }

    @Override
    public Boolean getTokenText() {
        return value;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visit.visit(this);
    }

}
