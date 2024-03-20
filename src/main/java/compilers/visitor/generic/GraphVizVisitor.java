package compilers.visitor.generic;

import compilers.ast.GenericNode;
import guru.nidi.graphviz.model.MutableGraph;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Stack;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

@Getter
public class GraphVizVisitor extends GenericVisitor {
    private final MutableGraph graph;
    private GenericNode curNode;
    private GenericNode prevNode;
    private final GenericNode rootNode;
    private final Stack<GenericNode> stack;


    public GraphVizVisitor(GenericNode rootNode) {
        this.rootNode = rootNode;
        this.stack = new Stack<>();
        graph = mutGraph("KXI_AST").setDirected(true);
        accept(rootNode);
    }

    @Override
    public void visit(GenericNode genericNode) {
        stack.pop();
        if (stack.empty()) curNode = rootNode;
        else curNode = stack.peek();
    }

    @Override
    public void preVisit(GenericNode genericNode) {
        stack.push(genericNode);
        if (curNode != null) {
            prevNode = curNode;
        }
        curNode = genericNode;
        if (prevNode != null && curNode != null) {
            String prevName = prevNode.getClass().getSimpleName() + prevNode.hashCode();
            String curName = curNode.getClass().getSimpleName() + curNode.hashCode();

            graph.add(mutNode(prevName).addLink(curName));
        }
    }
}
