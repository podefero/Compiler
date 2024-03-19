package compilers.ast.kxi_nodes.scope;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;

public abstract class AbstractKxiScope extends AbstractKxiNode {
    public AbstractKxiScope(GenericNode... genericNodes) {
        super(genericNodes);
    }

}
