package compilers.ast.kxi_nodes.scope;

import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiBlock extends AbstractKxiScope{
    private List<AbstractKxiStatement> statements;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        visitList(statements, visit);
    }
}
