package compilers.ast.kxi_nodes.expressions;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;

public abstract class AbstractKxiExpression extends AbstractKxiNode {

    public AbstractKxiExpression(GenericNode ... genericNodes) {
        super(genericNodes);
    }
}
