package compilers.ast.intermediate.statements;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.Data;

import java.util.List;

@Data
public abstract class InterStatement extends AbstractInterNode {
    protected InterOperand interOperand;
    protected List<InterExpression> interExpressionList;
    public InterStatement(GenericNode... genericNodes) {
        super(genericNodes);
    }
}
