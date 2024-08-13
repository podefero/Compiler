package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterBlock extends InterStatement {
    List<InterStatement> interStatementList;

    public InterBlock(GenericListNode statements) {
        super(statements);
        this.interStatementList = getNodeList(statements);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
