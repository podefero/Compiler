package compilers.visitor;

import compilers.ast.kxi_nodes.KxiMain;
import guru.nidi.graphviz.model.MutableGraph;
import lombok.Getter;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

@Getter
public class GraphVizVisitor implements VisitKxi{
    private MutableGraph graph;
    @Override
    public void visitKxiMain(KxiMain kxiMain) {
        graph = mutGraph("example").setDirected(true).add(mutNode("a").addLink(mutNode("b")));

    }
}
