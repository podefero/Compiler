package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;

public class InterBinaryAssignmentOp extends InterOperation{

    public InterBinaryAssignmentOp(InterOperand leftOperand, InterOperand rightOperand) {
        super(rightOperand, leftOperand);
    }
}
