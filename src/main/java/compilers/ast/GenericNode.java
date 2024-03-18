package compilers.ast;

import java.util.List;

public abstract class GenericNode<V> {
    public void accept(V visit) {
    }

    protected void visitChildren(V visit) {
        visitChildren(visit);
    }

    protected void visitList(List<GenericNode>list, V visit) {
        list.stream()
                .forEach(node -> node.accept(visit));
    }
}
