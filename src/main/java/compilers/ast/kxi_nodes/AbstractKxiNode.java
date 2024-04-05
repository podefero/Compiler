package compilers.ast.kxi_nodes;

import compilers.ast.GenericNode;
import lombok.Setter;

@Setter
public abstract class AbstractKxiNode extends GenericNode {
    private String lineInfo;
    public AbstractKxiNode(GenericNode ... genericNodes) {
        super(genericNodes);
    }
}
