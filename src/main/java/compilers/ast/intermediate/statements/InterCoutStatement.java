package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.InterOperation;
import lombok.Getter;

import java.util.List;

@Getter
public class InterCoutStatement extends InterStatement {
    private List<InterExpression> expressionList;
    private InterOperation operation;

    public InterCoutStatement(GenericListNode expressions, InterOperation operation) {
        super(expressions, operation);
        this.expressionList = getNodeList(expressions);
        this.operation = operation;
    }
}
