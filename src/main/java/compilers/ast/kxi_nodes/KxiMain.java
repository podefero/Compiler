package compilers.ast.kxi_nodes;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class KxiMain extends AbstractKxiNode {
    private KxiBlock block;
    private IdentifierToken identifierToken;
    String id;
    private List<KxiClass> classList;

    public KxiMain(KxiBlock block, IdentifierToken identifierToken, GenericListNode classList) {
        super(block, classList);
        this.block = block;
        this.identifierToken = identifierToken;
        this.classList = getNodeList(classList);
        id = identifierToken.getValue();
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
