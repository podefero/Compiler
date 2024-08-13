package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperandReturn extends InterOperand{
    boolean left;
    boolean argumentValue;
    public OperandReturn(InterValue interValue, boolean left) {
        super(interValue);
        this.left = left;
        this.argumentValue = false;
    }

    @Override
    public GenericNode copy() {
        return new OperandReturn((InterValue) interValue.copy(), left);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }}
