package compilers.ast.kxi_nodes.expressions.binary;

import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.ast.intermediate.expression.InterVariable;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.literals.ExpressionIdLit;
import compilers.ast.kxi_nodes.expressions.literals.ExpressionLiteral;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Data;

@Data
public abstract class AbstractKxiBinaryOperation extends AbstractKxiExpression {
    protected AbstractKxiExpression expressionR;
    protected AbstractKxiExpression expressionL;
    protected ExpressionIdLit tempId;

    public AbstractKxiBinaryOperation(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
        this.expressionR = expressionR;
        this.expressionL = expressionL;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
    }
}
