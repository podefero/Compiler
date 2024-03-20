package compilers.ast.kxi_nodes.statements;

import compilers.visitor.kxi.KxiVisitorBase;

public class KxiBreakStatement extends AbstractKxiStatement{
    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visit.visit(this);
    }

}
