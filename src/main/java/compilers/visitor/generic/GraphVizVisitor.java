package compilers.visitor.generic;

import compilers.ast.GenericNode;
import compilers.ast.GenericTerminal;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiAbstractType;
import compilers.ast.kxi_nodes.token_literals.TokenLiteral;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import lombok.Getter;

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

    private String getLabel(GenericNode genericNode) {
        if (genericNode instanceof GenericTerminal) {
            return ((GenericTerminal) curNode).getTerminalValue();
        } else {
            return genericNode.getClass().getSimpleName();
        }
    }

    private String getHash(GenericNode genericNode) {
        return "" + genericNode.hashCode();
    }

    @Override
    public void preVisit(GenericNode genericNode) {
        stack.push(genericNode);
        if (curNode != null) {
            prevNode = curNode;
        }
        curNode = genericNode;
        if (prevNode != null && curNode != null) {
            String prevNodeId = getHash(prevNode);
            String curNodeId = getHash(curNode);
            String label = getLabel(curNode);

            // Create nodes with hash IDs and labels
            MutableNode prevGraphNode = mutNode(prevNodeId).add(Label.of(getLabel(prevNode)));
            MutableNode curGraphNode = mutNode(curNodeId).add(Label.of(label));

            // Add nodes to the graph and connect them with an edge
            graph.add(prevGraphNode);
            graph.add(curGraphNode);
            graph.add(prevGraphNode.addLink(curGraphNode));
        }
    }
}


