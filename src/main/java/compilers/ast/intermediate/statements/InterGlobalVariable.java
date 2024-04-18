package compilers.ast.intermediate.statements;

import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.*;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter

public class InterGlobalVariable extends InterStatement {
    private InterPtr interId;
    private Directive directive;
    private InterLit interLit;

    public InterGlobalVariable(InterPtr interId, Directive directive, InterLit interLit) {
        super(interId, interLit);
        this.directive = directive;
        this.interId = interId;
        this.interLit = interLit;
    }


    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
