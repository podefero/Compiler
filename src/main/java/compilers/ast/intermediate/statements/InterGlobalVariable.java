package compilers.ast.intermediate.statements;

import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterIdDir;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter

public class InterGlobalVariable extends InterStatement {
    private InterIdDir interId;
    private InterOperation interOperation;
    private Directive directive;
    String label;

    public InterGlobalVariable(InterIdDir interId, InterOperation interOperation, Directive directive) {
        super(interOperation, interId);
        this.interId = interId;
        this.interOperation = interOperation;
        this.directive = directive;
        label = convertIdToLabel(interId.getId());
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
