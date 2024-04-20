package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.GenericTerminal;
import compilers.ast.intermediate.InterArgs;
import compilers.ast.intermediate.InterId;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;


@Getter
public class InterFunctionalCall extends InterStatement  {
    InterId calleeId;
    List<InterArgs> interArgsList;
    String label;
    int numOfArgs;
    public InterFunctionalCall(InterId calleeId, GenericListNode args) {
        super(args);
        this.calleeId = calleeId;
        this.interArgsList = getNodeList(args);
        label = convertIdToLabel(calleeId.getId());
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
