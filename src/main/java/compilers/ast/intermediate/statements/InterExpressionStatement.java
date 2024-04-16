package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.expression.InterExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterExpressionStatement extends InterStatement {
    InterExpression expression;

    public InterExpressionStatement(InterExpression expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
