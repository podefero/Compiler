package compilers.ast.intermediate.InterOperand;

import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.InterValue;
import lombok.Getter;

@Getter
public abstract class InterOperand extends AbstractInterNode {
    protected InterValue interValue;

    public InterOperand(InterValue interValue) {
        this.interValue = interValue;

    }
}
