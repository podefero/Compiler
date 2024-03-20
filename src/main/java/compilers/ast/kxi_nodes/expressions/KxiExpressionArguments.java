package compilers.ast.kxi_nodes.expressions;

import compilers.ast.GenericListNode;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiExpressionArguments extends AbstractKxiExpression{
    private List<AbstractKxiExpression> arguments;
    private AbstractKxiExpression methodExpression;

    public KxiExpressionArguments(GenericListNode arguments, AbstractKxiExpression methodExpression) {
        super(arguments, methodExpression);
        this.arguments = getNodeList(arguments);
        this.methodExpression = methodExpression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
