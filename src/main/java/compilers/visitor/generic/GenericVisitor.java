package compilers.visitor.generic;

import compilers.ast.GenericListNode;
import compilers.ast.GenericNode;

/**
 * Used to visit nodes at an abstract/generic level
 */
public abstract class GenericVisitor {

    public<T extends GenericNode> void visit(T node) {

    }

    public<T extends GenericNode> void preVisit(T node) {
    }

    protected <T extends GenericNode> void visitChildren(T node) {
        if (node == null) return;
        node.getChildren()
                .forEach(n -> {
                    if (n instanceof GenericListNode) {
                        ((GenericListNode) n).getList()
                                .forEach(nodeList -> accept(nodeList));
                    } else {
                        accept(n);
                    }
                });
    }

    public<T extends GenericNode> void accept(T genericNode) {
        preVisit(genericNode);
        visitChildren(genericNode);
        visit(genericNode);
    }

}
