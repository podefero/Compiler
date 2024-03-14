package compilers.ast;

import compilers.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class AbstractKxiNode {
    List<AbstractKxiNode> children;
    public AbstractKxiNode(AbstractKxiNode ... nodes){
        children = new ArrayList<>(List.of(nodes));
    }

    public void accept(Visitor visit) {

    }
}
