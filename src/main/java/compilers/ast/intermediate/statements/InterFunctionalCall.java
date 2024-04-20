package compilers.ast.intermediate.statements;

import compilers.ast.GenericTerminal;
import compilers.ast.intermediate.InterId;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;


@Getter
public class InterFunctionalCall extends InterStatement implements GenericTerminal {
    InterId calleeId;
    String label;
    public InterFunctionalCall(InterId calleeId) {
        this.calleeId = calleeId;
        label = convertIdToLabel(calleeId.getId());
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    public String getTerminalValue() {
        return calleeId.getTerminalValue() + "()";
    }
}
