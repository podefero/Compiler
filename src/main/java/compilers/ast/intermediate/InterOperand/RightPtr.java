package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterPtr;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class RightPtr extends InterOperand{
    private InterPtr interPtr;
    public RightPtr(InterValue interValue) {
        super(interValue);
        this.interPtr = (InterPtr) interValue;
    }

    @Override
    public GenericNode copy() {
        RightPtr variableStack = new RightPtr(interValue);
        return variableStack;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }}
