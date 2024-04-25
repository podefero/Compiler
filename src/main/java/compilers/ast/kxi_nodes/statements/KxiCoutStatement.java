package compilers.ast.kxi_nodes.statements;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KxiCoutStatement extends AbstractKxiStatement {
    private AbstractKxiExpression expression;
    private ScalarType scalarType;
    public KxiCoutStatement(AbstractKxiExpression expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        acceptAbstractKxi(visit);
        visit.visit(this);
    }
}
