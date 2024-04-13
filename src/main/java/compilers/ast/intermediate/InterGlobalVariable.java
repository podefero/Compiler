package compilers.ast.intermediate;

import lombok.Getter;

@Getter
public class InterGlobalVariable extends AbstractInterNode {
    private InterVariable interVariable;

    public InterGlobalVariable(InterVariable interVariable) {
        super(interVariable);
        this.interVariable = interVariable;
    }
}
