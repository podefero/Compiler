package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.visitor.kxi.KxiVisitorBase;

public class InterUnarySubOperator extends InterOperation{

    public InterUnarySubOperator(InterOperand leftOperand, InterOperand rightOperand) {
        super(rightOperand, leftOperand);
    }

    public InterId getInterId() {
        return (InterId) leftOperand.getInterValue();
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }

}
