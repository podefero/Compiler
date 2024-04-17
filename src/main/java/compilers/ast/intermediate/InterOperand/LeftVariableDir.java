package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class LeftVariableDir extends InterOperand {
    private InterValue interValue;
    private Directive directive;

    public LeftVariableDir(InterValue interValue, Directive directive) {
        super(interValue);
        this.interValue = interValue;
        this.directive = directive;
    }

    @Override
    public GenericNode copy() {
        LeftVariableDir variableStack = new LeftVariableDir(interValue, directive);
        return variableStack;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
