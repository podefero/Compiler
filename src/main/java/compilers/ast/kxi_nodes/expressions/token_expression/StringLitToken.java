package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.VisitKxi;

public class StringLitToken extends TokenType<String> {
    public StringLitToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.STRING;
        value = tokenText;
    }


    @Override
    public String getTokenText() {
        EncodeCharacters encodeCharacters = new EncodeCharacters();
        return encodeCharacters.encodeText(value);
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visit.visit(this);
    }




}
