package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.visitor.kxi.KxiVisitorBase;

public class InterBinarySubtract extends InterOperation{


    public InterBinarySubtract(InterOperand leftOperand, InterOperand rightOperand) {
        super(rightOperand, leftOperand);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
