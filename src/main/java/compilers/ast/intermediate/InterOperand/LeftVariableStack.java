package compilers.ast.intermediate.InterOperand;

import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;

public class LeftVariableStack extends InterOperand{
    public LeftVariableStack(InterValue interValue) {
        super(interValue);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
