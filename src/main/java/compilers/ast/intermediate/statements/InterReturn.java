package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.Getter;

import java.util.List;

@Getter
public class InterReturn extends AbstractInterNode {
    private List<InterExpression> expressions;

    public InterReturn(GenericListNode expressions) {
        super(expressions);
        this.expressions = getNodeList(expressions);
    }

}
