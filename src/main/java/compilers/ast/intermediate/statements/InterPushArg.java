package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterOperand.InterOperand;
import lombok.Getter;

@Getter
public class InterPushArg extends InterStatement {
    InterOperand interOperands;

    public InterPushArg(InterOperand operand) {
        super(operand);
        this.interOperands = operand;

    }
}
