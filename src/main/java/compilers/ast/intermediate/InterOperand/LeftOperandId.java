package compilers.ast.intermediate.InterOperand;

import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;

public class LeftOperandId extends InterOperand{
    public LeftOperandId(InterValue interValue) {
        super(interValue);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
