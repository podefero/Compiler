package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;

public class InterBinaryAssignmentStatement extends InterStatement {
    InterOperation interOperation;
    InterVariable interVariable;

    public InterBinaryAssignmentStatement(InterOperation interOperation, InterVariable interVariable) {
        super(interOperation, interVariable);
        this.interOperation = interOperation;
        this.interVariable = interVariable;

    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
