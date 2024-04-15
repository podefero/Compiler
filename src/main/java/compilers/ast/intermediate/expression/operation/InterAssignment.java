package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;

public class InterAssignment extends InterOperation{
    public InterAssignment(InterOperand leftOperand, InterOperand rightOperand) {
        super(rightOperand, leftOperand);
    }
}
