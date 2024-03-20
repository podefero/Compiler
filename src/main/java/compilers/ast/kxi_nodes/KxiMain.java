package compilers.ast.kxi_nodes;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiMain extends AbstractKxiNode {
    private KxiBlock block;
    private IdentifierToken id;
    private List<KxiClass> classList;

    public KxiMain(KxiBlock block, IdentifierToken id, GenericListNode classList) {
        super(block, id, classList);
        this.block = block;
        this.id = id;
        this.classList = getNodeList(classList);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
