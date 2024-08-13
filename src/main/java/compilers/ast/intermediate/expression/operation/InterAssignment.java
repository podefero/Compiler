package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.visitor.kxi.KxiVisitorBase;

public class InterAssignment extends InterOperation{


    public InterAssignment(InterOperand rightOperand, InterOperand leftOperand) {
        super(rightOperand, leftOperand);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
