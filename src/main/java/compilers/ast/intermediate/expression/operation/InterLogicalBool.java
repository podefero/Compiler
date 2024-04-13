package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.OpCodes;

public class InterLogicalBool extends InterOperation{
    public InterLogicalBool(OpCodes opCodes, InterValue leftValue, InterValue rightValue) {
        super(opCodes, leftValue, rightValue);
    }
}
