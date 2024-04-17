package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class RightVariableDir extends InterOperand{
    private InterValue interValue;
    public RightVariableDir(InterValue interValue) {
        super(interValue);
        this.interValue = interValue;
    }

    @Override
    public GenericNode copy() {
        RightVariableDir variableStack = new RightVariableDir(interValue);
        return variableStack;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }}
