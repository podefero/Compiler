package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterId;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;


@Getter
public class InterFunctionalCall extends InterStatement {
    InterId calleeId;

    public InterFunctionalCall(InterId calleeId) {
        this.calleeId = calleeId;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        //visitChildren(visit);
        visit.visit(this);
    }
}
