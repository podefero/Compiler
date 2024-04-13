package compilers.ast.intermediate;

import compilers.ast.intermediate.expression.operation.InterOperation;
import lombok.Getter;

@Getter
public class InterVariable extends AbstractInterNode {
    private InterOperation interOperation;
    private InterId interId;

    public InterVariable(InterOperation interOperation, InterId interId) {
        super(interOperation, interId);
        this.interOperation = interOperation;
        this.interId = interId;
    }
}
