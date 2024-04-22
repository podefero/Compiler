package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.expression.operation.InterAssignment;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterBinaryAssignmentStatementGlobal extends InterStatement {
    InterOperation interArithmic;
    InterAssignment interAssignment;

    public InterBinaryAssignmentStatementGlobal(InterOperation interOperation, InterAssignment interAssignment) {
        super(interOperation, interAssignment);
        this.interArithmic = interOperation;
        this.interAssignment = interAssignment;

    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
