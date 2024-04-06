package compilers.visitor.generic;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiAbstractType;
import compilers.ast.kxi_nodes.token_literals.TokenLiteral;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.model.MutableGraph;
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

    @Override
    public void preVisit(GenericNode genericNode) {
        stack.push(genericNode);
        if (curNode != null) {
            prevNode = curNode;
        }
        curNode = genericNode;
        if (prevNode != null && curNode != null) {
            String prevName;
            String curName;
            if (curNode instanceof TokenLiteral) {
                curName = ((TokenLiteral<?>) curNode).getValue().toString() + "\n(" + curNode.getClass().getSimpleName() + curNode.hashCode() + ")";
            } else if (curNode instanceof KxiAbstractType) {
                curName = ((KxiAbstractType) curNode).getName("", (KxiAbstractType) curNode)+ "\n(" + curNode.getClass().getSimpleName() + curNode.hashCode() + ")";
            }else {
                curName = curNode.getClass().getSimpleName() + curNode.hashCode();
            }
            prevName = prevNode.getClass().getSimpleName() + prevNode.hashCode();

            graph.add(mutNode(prevName).addLink(curName));
        }
    }
}
