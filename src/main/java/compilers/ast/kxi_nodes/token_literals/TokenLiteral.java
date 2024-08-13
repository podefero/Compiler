package compilers.ast.kxi_nodes.token_literals;

import compilers.ast.GenericTerminal;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.ScalarType;
import lombok.Data;
import lombok.Getter;

@Data
public abstract class TokenLiteral<T> extends AbstractKxiNode implements GenericTerminal {
    protected T value;
    protected ScalarType scalarType;

    public TokenLiteral(String tokenText) {

    }

    public T getTokenText() {
        return null;
    }

    @Override
    public String getTerminalValue() {
        return value.toString();
    }
}
