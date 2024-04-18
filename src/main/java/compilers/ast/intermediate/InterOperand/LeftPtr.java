package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class LeftPtr extends InterOperand {
    private InterValue interValue;

    public LeftPtr(InterValue interValue) {
        super(interValue);
        this.interValue = interValue;
    }

    @Override
    public GenericNode copy() {
        LeftPtr variableStack = new LeftPtr(interValue);
        return variableStack;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
