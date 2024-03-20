package compilers.ast;


import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class GenericNode {
    protected List<GenericNode> children;

    public GenericNode(GenericNode... children) {
        this.children = createList(children);
        //left to right order
        //Collections.reverse(this.children);
    }

    protected <T extends GenericNode> List<T> getNodeList(GenericListNode genericListNode) {
        return (List<T>) genericListNode.getList();
    }


    public void accept(KxiVisitorBase visit) {
    }

    private List<GenericNode> createList(GenericNode[] nodes) {
        List<GenericNode> genericNodeList = new ArrayList<>();
        if(nodes.length == 0) return genericNodeList;
        for (GenericNode node : nodes) {
            genericNodeList.add(node);
        }

        return genericNodeList;
    }

    protected void visitChildren(KxiVisitorBase visit) {
        for (GenericNode child : children) {
            if (child instanceof GenericListNode) {
                visitList(((GenericListNode) child).getList(), visit);
            } else {
                child.accept(visit);
            }
        }
    }

    protected <T extends GenericNode> void visitList(List<T> list, KxiVisitorBase visit) {
        list.stream()
                .forEach(node -> node.accept(visit));
    }

}
