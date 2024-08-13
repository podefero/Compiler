package compilers.ast.kxi_nodes.statements;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Data;

@Data
public class KxiBreakStatement extends AbstractKxiStatement{
    String exitLoop;
    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visit.visit(this);
        acceptAbstractKxi(visit);
    }

}
