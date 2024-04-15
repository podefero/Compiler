package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterId;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterFunctionalCall extends InterStatement {
    List<InterPushArg> args;
    InterId calleeId;
    public InterFunctionalCall(GenericListNode args, InterId calleeId) {
        super(args);
        this.args = getNodeList(args);
        this.calleeId = calleeId;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
