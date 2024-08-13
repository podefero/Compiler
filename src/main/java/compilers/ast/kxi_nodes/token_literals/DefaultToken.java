package compilers.ast.kxi_nodes.token_literals;

import compilers.ast.kxi_nodes.ScalarType;

public class DefaultToken extends TokenLiteral<String> {

    public DefaultToken(String tokenText) {
        super(tokenText);
        scalarType = ScalarType.UNKNOWN;
        value = tokenText;
    }

    @Override
    public String getTokenText() {
        return value;
    }
}
