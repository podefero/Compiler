package compilers.ast.intermediate.statements;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.Data;

import java.util.List;

@Data
public abstract class InterStatement extends AbstractInterNode {
    protected List<InterExpression> interExpressionList;
    public InterStatement(GenericNode... genericNodes) {
        super(genericNodes);
    }
}
