package compilers.ast.kxi_nodes.expressions.binary.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;

public class KxiEqualsEquals extends AbstractBinaryConditionalExpression {
    public KxiEqualsEquals(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        super.accept(visit);
        visit.visit(this);
    }
}
