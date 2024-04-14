package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.OpCodes;

public class InterAssignment extends InterOperation{
    public InterAssignment(InterValue leftValue, InterValue rightValue) {
        super(leftValue, rightValue);
    }
}
