package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterArgs;
import compilers.ast.intermediate.InterId;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;


@Getter
public class InterFunctionalCall extends InterStatement {
    InterId calleeId;
    List<InterArgs> interArgList;

    public InterFunctionalCall(InterId calleeId, GenericListNode args) {
        super(args);
        this.calleeId = calleeId;
        this.interArgList = getNodeList(args);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
