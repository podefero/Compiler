package compilers.visitor;

import compilers.ast.kxi_nodes.KxiMain;
import compilers.visitor.kxi.KxiVisitorBase;
import guru.nidi.graphviz.model.MutableGraph;
import lombok.Getter;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

@Getter
public class GraphVizVisitor extends KxiVisitorBase {
    private MutableGraph graph;

    public GraphVizVisitor() {
        graph = mutGraph("KXI_AST").setDirected(true);
    }

    @Override
    public void visit(KxiMain kxiMain) {
        graph.add(mutNode("a").addLink(mutNode("b")));
        graph.add(mutNode("a").addLink(mutNode("c")));
    }

}
