package compilers.ast.kxi_nodes.statements;

import compilers.visitor.kxi.VisitKxi;

public class KxiBreakStatement extends AbstractKxiStatement{
    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visit.visit(this);
    }

}
