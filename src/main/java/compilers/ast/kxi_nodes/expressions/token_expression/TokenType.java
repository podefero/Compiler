package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public abstract class TokenType<T> extends AbstractKxiExpression {
    private T value;
    public TokenType(String tokenText) {

    }

    public T getValue() {
        return value;
    }
}
