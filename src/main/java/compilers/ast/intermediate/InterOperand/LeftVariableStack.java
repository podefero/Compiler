package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class LeftVariableStack extends InterOperand{
    public LeftVariableStack(InterValue interValue) {
        super(interValue);
    }

    public InterId getInterId() {
        return (InterId) this.interValue;
    }

    @Override
    public GenericNode copy() {
        LeftVariableStack variableStack = new LeftVariableStack((InterValue) interValue.copy());
        return variableStack;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
