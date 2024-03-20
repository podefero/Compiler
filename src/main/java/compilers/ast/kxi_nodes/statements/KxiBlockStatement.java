package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class KxiBlockStatement extends AbstractKxiStatement {
    private KxiBlock block;

    public KxiBlockStatement(KxiBlock block) {
        super(block);
        this.block = block;
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
