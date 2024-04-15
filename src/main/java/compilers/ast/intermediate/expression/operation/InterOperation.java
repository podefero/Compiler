package compilers.ast.intermediate.expression.operation;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.Getter;

@Getter
public abstract class InterOperation extends InterExpression {
    protected InterOperand leftOperand;
    protected InterOperand rightOperand;

    public InterOperation(InterOperand rightOperand, InterOperand leftOperand) {
        super(rightOperand, leftOperand);
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }
}
