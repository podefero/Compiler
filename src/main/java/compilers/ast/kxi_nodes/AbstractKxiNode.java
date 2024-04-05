package compilers.ast.kxi_nodes;

import compilers.ast.GenericNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractKxiNode extends GenericNode {
    protected String lineInfo;
    public AbstractKxiNode(GenericNode ... genericNodes) {
        super(genericNodes);
    }
}
