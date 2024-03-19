package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.VisitKxi;

public class CharLitToken extends TokenType<Character> {


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
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visit.visit(this);
    }
}
