package compilers.ast.kxi_nodes.expressions;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public
class KxiNewExpressionArgument extends AbstractKxiExpression{
    private List<AbstractKxiExpression> arguments;
    private IdentifierToken id;

    public KxiNewExpressionArgument(GenericListNode arguments, IdentifierToken id) {
        this.arguments = getNodeList(arguments);
        this.id = id;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
