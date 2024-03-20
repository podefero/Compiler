package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiBlockStatement extends AbstractKxiStatement {
    private KxiBlock block;

    public KxiBlockStatement(KxiBlock block) {
        super(block);
        this.block = block;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
