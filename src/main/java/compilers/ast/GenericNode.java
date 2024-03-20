package compilers.ast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class GenericNode<V> {
    protected List<GenericNode> children;

    public GenericNode(GenericNode... children) {
        this.children = createList(children);
        //left to right order
        Collections.reverse(this.children);
    }

    protected <T extends GenericNode> List<T> getNodeList(GenericListNode genericListNode) {
        return (List<T>) genericListNode.getList();
    }


    public void accept(V visit) {

    }

    private List<GenericNode> createList(GenericNode[] nodes) {
        List<GenericNode> genericNodeList = new ArrayList<>();
        if(nodes.length == 0) return genericNodeList;
        for (GenericNode node : nodes) {
            if (node == null) genericNodeList.add(null);
            else genericNodeList.add(node);
        }

        return genericNodeList;
    }

    protected void visitChildren(V visit) {
        for (GenericNode child : children) {
            if (child instanceof GenericListNode) {
                visitList(((GenericListNode) child).getList(), visit);
            } else {
                child.accept(visit);
            }
        }
    }

    protected <T extends GenericNode> void visitNode(Optional<T> node, V visit) {
        if (!node.isEmpty()) node.get().accept(visit);
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
