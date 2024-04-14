package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.OpCodes;

public class InterRelational extends InterOperation{
    public InterRelational(InterValue leftValue, InterValue rightValue) {
        super(leftValue, rightValue);
    }
}
