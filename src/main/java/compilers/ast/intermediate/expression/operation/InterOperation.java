package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.Assemble;
import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.OpCodes;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public abstract class InterOperation extends InterExpression {
    protected OpCodes opCodes;
    protected InterValue leftValue;
    protected InterValue rightValue;

    public InterOperation(OpCodes opCodes, InterValue leftValue, InterValue rightValue) {
        super(leftValue, rightValue);
        this.opCodes = opCodes;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }
}
