package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class KxiMain extends AbstractKxiNode {
    private KxiBlock block;
    private IdentifierToken id;
    private List<KxiClass> classList;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        visitList(classList, visit);
        id.accept(visit);
        block.accept(visit);
    }
}
