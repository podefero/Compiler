package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.OpCodes;

public class InterBinaryOp extends InterOperation{
    public InterBinaryOp(OpCodes opCodes, InterValue leftValue, InterValue rightValue) {
        super(opCodes, leftValue, rightValue);
    }
}
