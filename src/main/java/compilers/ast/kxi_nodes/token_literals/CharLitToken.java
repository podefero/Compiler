package compilers.ast.kxi_nodes.token_literals;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;

public class CharLitToken extends TokenLiteral<Character> {


    public CharLitToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.CHAR;
        value = new EncodeCharacters().encodeText(tokenText).charAt(0);
    }

    @Override
    public Character getTokenText() {
        return value;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visit.visit(this);
    }
}
