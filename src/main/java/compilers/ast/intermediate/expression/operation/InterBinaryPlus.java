package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterValue;

public class InterBinaryPlus extends InterOperation{
    public InterBinaryPlus(InterValue leftValue, InterValue rightValue) {
        super(leftValue, rightValue);
    }
}
