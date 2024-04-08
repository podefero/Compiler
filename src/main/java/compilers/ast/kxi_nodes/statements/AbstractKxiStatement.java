package compilers.ast.kxi_nodes.statements;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.visitor.kxi.KxiVisitorBase;

public abstract class AbstractKxiStatement extends AbstractKxiNode {
    public AbstractKxiStatement(GenericNode... genericNodes) {
        super(genericNodes);
    }

    @Override
    public void acceptAbstractKxi(KxiVisitorBase kxiVisitorBase) {
        kxiVisitorBase.visitStatement(this);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        acceptAbstractKxi(visit);
    }
}
