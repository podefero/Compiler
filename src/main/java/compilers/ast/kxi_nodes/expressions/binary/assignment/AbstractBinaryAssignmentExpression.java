package compilers.ast.kxi_nodes.expressions.binary.assignment;

import compilers.ast.intermediate.expression.operation.InterAssignment;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.ast.kxi_nodes.expressions.literals.ExpressionIdLit;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractBinaryAssignmentExpression extends AbstractKxiBinaryOperation {

    protected ExpressionIdLit id;

    public AbstractBinaryAssignmentExpression(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
    }

    @Override
    public void acceptAbstractKxi(KxiVisitorBase kxiVisitorBase) {
        kxiVisitorBase.visitAssignment(this);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        super.accept(visit);
        acceptAbstractKxi(visit);
    }
}
