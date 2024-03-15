package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.ScalarType;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public abstract class TokenType<T> extends AbstractKxiExpression {
    protected T value;
    protected ScalarType scalarType;

    public TokenType(String tokenText) {

    }

    public T getTokenText() {
        return null;
    }
}
