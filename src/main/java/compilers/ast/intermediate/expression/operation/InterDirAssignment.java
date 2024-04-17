package compilers.ast.intermediate.expression.operation;

import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.visitor.kxi.KxiVisitorBase;

public class InterDirAssignment extends InterOperation{
    Directive directive;

    public InterDirAssignment(InterOperand leftOperand, InterOperand rightOperand, Directive directive) {
        super(rightOperand, leftOperand);
        this.directive = directive;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
