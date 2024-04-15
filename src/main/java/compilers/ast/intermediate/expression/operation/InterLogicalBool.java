package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;

public class InterLogicalBool extends InterOperation{
    public InterLogicalBool(InterOperand leftOperand, InterOperand rightOperand) {
        super(rightOperand, leftOperand);
    }
}
