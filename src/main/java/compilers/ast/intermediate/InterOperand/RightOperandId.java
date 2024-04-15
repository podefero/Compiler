package compilers.ast.intermediate.InterOperand;

import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class RightOperandId extends InterOperand{
    private InterId interId;
    public RightOperandId(InterValue interValue) {
        super(interValue);
        this.interId = (InterId) interValue;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }}
