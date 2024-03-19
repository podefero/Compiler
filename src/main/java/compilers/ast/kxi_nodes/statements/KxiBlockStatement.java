package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiBlockStatement extends AbstractKxiStatement {
    private KxiBlock block;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        block.accept(visit);
    }
}
