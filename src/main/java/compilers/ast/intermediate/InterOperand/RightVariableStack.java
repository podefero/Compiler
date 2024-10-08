package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class RightVariableStack extends InterOperand{
//    private InterId interId;
    public RightVariableStack(InterValue interValue) {
        super(interValue);
//        this.interId = (InterId) interValue;
    }

    public InterId getInterId() {
        return (InterId) this.interValue;
    }

    @Override
    public GenericNode copy() {
        RightVariableStack variableStack = new RightVariableStack((InterValue) interValue.copy());
        return variableStack;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }}
