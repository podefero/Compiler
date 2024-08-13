package compilers.ast.kxi_nodes;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiArguments extends AbstractKxiNode {
    private List<AbstractKxiExpression> arguments;

    public KxiArguments(GenericListNode arguments) {
        super(arguments);
        this.arguments = getNodeList(arguments);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
