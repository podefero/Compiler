package compilers.ast.kxi_nodes.statements;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;

public abstract class AbstractKxiStatement extends AbstractKxiNode {
    public AbstractKxiStatement(GenericNode... genericNodes) {
        super(genericNodes);
    }
}
