package compilers.ast.kxi_nodes.expressions.uni;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public abstract class AbstractKxiUniOperation extends AbstractKxiExpression {
    protected AbstractKxiExpression expression;

    public AbstractKxiUniOperation(AbstractKxiExpression expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
    }

    @Override
    protected void visitChildren(KxiVisitorBase visit) {
        expression.accept(visit);
    }
}

