package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.InterLit;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class RightOperandLit extends InterOperand{
    InterLit interLit;
    public RightOperandLit(InterValue interValue) {
        super(interValue);
        this.interLit = (InterLit) interValue;
    }

    @Override
    public GenericNode copy() {
        RightOperandLit operandLit = new RightOperandLit(new InterLit<>(interLit.getValue(), interLit.getScalarType()));
        return operandLit;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
