package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.visitor.kxi.KxiVisitorBase;

public class InterEmptyOperator extends InterOperation{

    public InterEmptyOperator(InterOperand leftOperand, InterOperand rightOperand) {
        super(rightOperand, leftOperand);
    }

}
