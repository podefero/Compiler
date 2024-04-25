package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterValue;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;


@Getter
public class InterMainStart extends InterValue {
    InterId calleeId;
    String label;
    public InterMainStart(InterId calleeId, ScalarType scalarType) {
        super(scalarType);
        this.calleeId = calleeId;
        label = convertIdToLabel(calleeId.getId());
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    public String getTerminalValue() {
        return calleeId.getTerminalValue();
    }
}
