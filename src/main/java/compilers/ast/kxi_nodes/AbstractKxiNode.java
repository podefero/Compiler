package compilers.ast.kxi_nodes;

import compilers.ast.GenericNode;


public abstract class AbstractKxiNode extends GenericNode {
    public AbstractKxiNode(GenericNode ... genericNodes) {
        super(genericNodes);
    }
}
