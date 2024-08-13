package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.GenericTerminal;
import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.Data;
import lombok.Getter;

@Data
public abstract class InterOperand extends InterExpression implements GenericTerminal {
    protected InterValue interValue;

    public InterOperand(InterValue interValue) {
        this.interValue = interValue;

    }


    @Override
    public String getTerminalValue() {
        return interValue.getTerminalValue();
    }
}
