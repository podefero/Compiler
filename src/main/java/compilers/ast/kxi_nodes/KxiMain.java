package compilers.ast.kxi_nodes;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.visitor.kxi.VisitKxi;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiMain extends AbstractKxiNode {
    private final KxiBlock block;
    private final IdentifierToken id;
    private final List<KxiClass> classList;

    public KxiMain(GenericNode... genericNodes) {
        super(genericNodes);
        block = getNode(0);
        id = getNode(1);
        classList = getNodeList(2);
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
