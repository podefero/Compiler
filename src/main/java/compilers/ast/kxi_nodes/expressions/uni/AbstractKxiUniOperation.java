package compilers.ast.kxi_nodes.expressions.uni;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public abstract class AbstractKxiUniOperation extends AbstractKxiExpression {
    protected AbstractKxiExpression expression;

    public AbstractKxiUniOperation(AbstractKxiExpression expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public void accept(VisitKxi visit) {
        visitChildren(visit);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        expression.accept(visit);
    }
}

