package compilers.ast.intermediate.statements;

import lombok.Getter;

@Getter
public class InterWhile extends InterStatement {
    private InterIfStatement interIfStatement;

    public InterWhile(InterIfStatement interIfStatement) {
        super(interIfStatement);
        this.interIfStatement = interIfStatement;
    }

}
