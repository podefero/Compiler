package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import lombok.Getter;

@Getter
public class KxiParameter extends AbstractKxiNode {
    private IdentifierToken id;
    private KxiType type;

    public KxiParameter(IdentifierToken id, KxiType type) {
        super(id);
        this.id = id;
        this.type = type;
    }
}
