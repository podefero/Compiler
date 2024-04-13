package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.expression.operation.InterOperation;
import lombok.Getter;

import java.util.List;

@Getter
public class InterWhile extends InterStatements {
    private InterIfStatement interIfStatement;

    public InterWhile(InterIfStatement interIfStatement) {
        super(interIfStatement);
        this.interIfStatement = interIfStatement;
    }

}
