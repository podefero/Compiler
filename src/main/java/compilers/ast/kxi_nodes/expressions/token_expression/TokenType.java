package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import lombok.Getter;

@Getter
public abstract class TokenType<T> extends AbstractKxiExpression {
    protected T value;
    protected ScalarType scalarType;

    public TokenType(String tokenText) {

    }

    public T getTokenText() {
        return null;
    }
}
