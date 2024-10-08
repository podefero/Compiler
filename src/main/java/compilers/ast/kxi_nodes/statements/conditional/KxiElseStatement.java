package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.intermediate.statements.InterElseStatement;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KxiElseStatement extends AbstractKxiConditionalStatement {
    private AbstractKxiStatement statement;
    InterElseStatement interElseStatement;
    String ifNot;
    String done;

    public KxiElseStatement(AbstractKxiStatement statement) {
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
