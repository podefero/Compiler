package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.InterOperation;
import lombok.Getter;

import java.util.List;

@Getter
public class InterCoutStatement extends InterStatements{
    private List<InterExpression> expressionList;
    private InterOperation operation;

    public InterCoutStatement(GenericListNode expressions, InterOperation operation) {
        super(expressions, operation);
        this.expressionList = getNodeList(expressions);
        this.operation = operation;
    }
}
