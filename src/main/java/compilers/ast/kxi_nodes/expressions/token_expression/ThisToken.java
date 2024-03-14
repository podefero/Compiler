package compilers.ast.kxi_nodes.expressions.token_expression;

public class ThisToken extends TokenType<String> {
    private String identifier;

    public ThisToken(String tokenText) {
        super(tokenText);
        identifier = tokenText;
    }


    @Override
    public String getValue() {
        return identifier;
    }
}
