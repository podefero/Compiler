package compilers.ast.kxi_nodes.expressions.uni;

import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.ast.intermediate.expression.InterVariable;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractKxiUniOperation extends AbstractKxiExpression {
    protected AbstractKxiExpression expression;
    protected String tempId;
    public AbstractKxiUniOperation(AbstractKxiExpression expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
    }

}

