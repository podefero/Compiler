package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterIdDir;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter

public class InterGlobalVariable extends InterStatement {
    private InterIdDir interId;
    private InterOperation interOperation;

    public InterGlobalVariable(InterIdDir interId, InterOperation interOperation) {
        super(interOperation, interId);
        this.interId = interId;
        this.interOperation = interOperation;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
