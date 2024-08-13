package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.KxiArguments;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public
class KxiNewExpressionArgument extends AbstractKxiExpression{
    private KxiArguments arguments;
    private IdentifierToken id;

    public KxiNewExpressionArgument(KxiArguments arguments, IdentifierToken id) {
        super(arguments, id);
        this.arguments = arguments;
        this.id = id;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
