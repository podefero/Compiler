package compilers.ast.kxi_nodes.class_members;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;

public abstract class KxiAbstractKxiClassMember extends AbstractKxiNode {
    public KxiAbstractKxiClassMember(GenericNode... genericNodes) {
        super(genericNodes);
    }
}
