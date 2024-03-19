package compilers.ast.kxi_nodes;

import compilers.ast.GenericNode;
import compilers.visitor.kxi.VisitKxi;


public abstract class AbstractKxiNode extends GenericNode<VisitKxi> {
    public AbstractKxiNode(GenericNode ... genericNodes) {
        super(genericNodes);
    }
}
