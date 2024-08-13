package compilers.ast.intermediate;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.visitor.kxi.KxiVisitorBase;

public class InterBlob extends AbstractKxiNode {
    public InterBlob(GenericNode... genericNodes) {
        super(genericNodes);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
    }
}
