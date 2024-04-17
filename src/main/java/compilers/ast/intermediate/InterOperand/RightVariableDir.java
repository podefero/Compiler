package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class RightVariableDir extends InterOperand{
    private InterValue interValue;
    Directive directive;
    public RightVariableDir(InterValue interValue, Directive directive) {
        super(interValue);
        this.interValue = interValue;
        this.directive = directive;
    }

    @Override
    public GenericNode copy() {
        RightVariableDir variableStack = new RightVariableDir(interValue, directive);
        return variableStack;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }}
