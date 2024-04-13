package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.Getter;

import java.util.List;

@Getter
public class InterExpressionStatement extends InterStatements{
    List<InterExpression> expressions;

    public InterExpressionStatement(GenericListNode expressions) {
        this.expressions = getNodeList(expressions);
    }
}
