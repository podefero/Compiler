package compilers.ast.kxi_nodes.expressions.binary;

import compilers.ast.intermediate.statements.InterVariable;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Data;
import lombok.Getter;

@Data
public abstract class AbstractKxiBinaryOperation extends AbstractKxiExpression {
    protected AbstractKxiExpression expressionR;
    protected AbstractKxiExpression expressionL;
    protected InterVariable interVariable;

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
