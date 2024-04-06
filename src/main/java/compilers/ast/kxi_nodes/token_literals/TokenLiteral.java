package compilers.ast.kxi_nodes.token_literals;

import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.ScalarType;
import lombok.Getter;

@Getter
public abstract class TokenLiteral<T> extends AbstractKxiNode {
    protected T value;
    protected ScalarType scalarType;

    public TokenLiteral(String tokenText) {

    }

    public T getTokenText() {
        return null;
    }
}
