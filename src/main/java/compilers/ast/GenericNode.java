package compilers.ast;

import java.util.List;
import java.util.Optional;

public abstract class GenericNode<V> {
    public void accept(V visit) {
    }

    protected void visitChildren(V visit) {
    }

    protected <T extends GenericNode> void visitNode(Optional<T> node, V visit) {
        if(!node.isEmpty()) node.get().accept(visit);
    }

    protected <T extends GenericNode> void visitList(List<T> list, V visit) {
        list.stream()
                .forEach(node -> node.accept(visit));
    }

    protected <T extends GenericNode> void visitList(Optional<List<T>> list, V visit) {
        if (!list.isEmpty()) {
            list.get().stream()
                    .forEach(node -> node.accept(visit));
        }
    }
}
