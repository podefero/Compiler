package compilers.ast.kxi_nodes.expressions.binary.arithmic;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;

public class KxiMult extends AbstractBinaryArithmicExpression{
    public KxiMult(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        super.accept(visit);
        visit.visit(this);
    }
}
