package compilers.ast.kxi_nodes.expressions.token_expression;

public class DefaultToken extends TokenType<String> {
    private String value;

    public DefaultToken(String tokenText) {
        super(tokenText);
        value = tokenText;
    }


    @Override
    public String getValue() {
        return value;
    }
}
