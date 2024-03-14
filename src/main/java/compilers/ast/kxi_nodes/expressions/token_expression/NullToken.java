package compilers.ast.kxi_nodes.expressions.token_expression;

public class NullToken extends TokenType<String> {
    private String identifier;

    public NullToken(String tokenText) {
        super(tokenText);
        identifier = tokenText;
    }


    @Override
    public String getValue() {
        return identifier;
    }
}
