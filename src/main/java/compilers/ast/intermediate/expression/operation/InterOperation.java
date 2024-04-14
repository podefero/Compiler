package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.OpCodes;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.Getter;

@Getter
public abstract class InterOperation extends InterExpression {
    protected InterValue leftValue;
    protected InterValue rightValue;

    public InterOperation(InterValue leftValue, InterValue rightValue) {
        super(leftValue, rightValue);
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }
}
