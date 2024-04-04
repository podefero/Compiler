package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import lombok.Getter;

@Getter
public class KxiParameter extends AbstractKxiNode {
    private IdentifierToken id;
    private KxiAbstractType type;

    public KxiParameter(IdentifierToken id, KxiAbstractType type) {
        super(id);
        this.id = id;
        this.type = type;
    }
}
