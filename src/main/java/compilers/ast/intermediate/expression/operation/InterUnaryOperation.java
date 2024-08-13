package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.Data;

@Data
public abstract class InterUnaryOperation extends InterExpression {
    protected InterOperand rightOperand;

    public InterUnaryOperation(InterOperand rightOperand) {
        super(rightOperand);
        this.rightOperand = rightOperand;
    }
}
