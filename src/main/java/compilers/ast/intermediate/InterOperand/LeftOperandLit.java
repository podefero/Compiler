package compilers.ast.intermediate.InterOperand;

import compilers.ast.intermediate.InterLit;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class LeftOperandLit extends InterOperand{
    InterLit interLit;
    public LeftOperandLit(InterValue interValue) {
        super(interValue);
        this.interLit = (InterLit) interValue;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
