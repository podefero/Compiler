package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.visitor.kxi.KxiVisitorBase;

public class InterMethodOperation extends InterUnaryOperation{

    public InterMethodOperation(InterOperand rightOperand) {
        super(rightOperand);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
