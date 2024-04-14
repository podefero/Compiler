package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.expression.InterExpression;
import lombok.Getter;

@Getter
public class InterExpressionStatement extends InterStatement {
    InterExpression expression;

    public InterExpressionStatement(InterExpression expression) {
        this.expression = expression;
    }
}
