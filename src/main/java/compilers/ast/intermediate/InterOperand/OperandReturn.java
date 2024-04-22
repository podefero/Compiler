package compilers.ast.intermediate.InterOperand;

import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class OperandReturn extends InterOperand{
    boolean left;
    public OperandReturn(InterValue interValue, boolean left) {
        super(interValue);
        this.left = left;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }}
