package compilers.ast.kxi_nodes.expressions.token_expression;

public class StringLitToken extends TokenType<String> {
    private String value;

    public StringLitToken(String tokenText) {
        super(tokenText);
        value = tokenText;
    }


    @Override
    public String getValue() {
        EncodeCharacters encodeCharacters = new EncodeCharacters();
        return encodeCharacters.encodeText(value);
    }



}
