package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiElseStatement extends AbstractKxiConditionalStatement {
    private KxiBlock statement;


    public KxiElseStatement(KxiBlock statement) {
        super(statement);
        this.statement = statement;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        acceptAbstractKxi(visit);
        visit.visit(this);
    }

}
