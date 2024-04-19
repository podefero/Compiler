package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.expression.operation.InterAssignment;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterCinStatement extends InterStatement {
    ScalarType scalarType;
    InterAssignment interAssignment;
    public InterCinStatement(ScalarType scalarType, InterAssignment interAssignment) {
        super(interAssignment);
        this.scalarType = scalarType;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
