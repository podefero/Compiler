package compilers.ast.kxi_nodes.expressions.binary.arithmic;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Data;
import lombok.Setter;

public class KxiPlus extends AbstractBinaryArithmicExpression{
    public KxiPlus(AbstractKxiExpression expressionR, AbstractKxiExpression expressionL) {
        super(expressionR, expressionL);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        super.accept(visit);
        visit.visit(this);
    }
}

