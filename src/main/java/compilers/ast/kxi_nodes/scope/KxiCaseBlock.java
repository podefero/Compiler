package compilers.ast.kxi_nodes.scope;

import compilers.ast.kxi_nodes.KxiCaseInt;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiCaseBlock extends AbstractKxiScope {
    protected List<AbstractKxiStatement> defaultStatements;
    protected List<KxiCaseInt> cases;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        visitList(cases, visit);
        visitList(defaultStatements, visit);
    }
}
