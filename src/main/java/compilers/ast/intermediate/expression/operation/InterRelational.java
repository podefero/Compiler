package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;

public class InterRelational extends InterOperation{

    public InterRelational(InterOperand leftOperand, InterOperand rightOperand) {
        super(rightOperand, leftOperand);
    }
}
