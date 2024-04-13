package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.OpCodes;

public class InterBinaryAssignmentOp extends InterOperation{
    public InterBinaryAssignmentOp(OpCodes opCodes, InterValue leftValue, InterValue rightValue) {
        super(opCodes, leftValue, rightValue);
    }
}
