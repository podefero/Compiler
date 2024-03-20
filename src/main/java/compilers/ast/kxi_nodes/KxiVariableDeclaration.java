package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiVariableDeclaration extends AbstractKxiNode {
    private AbstractKxiExpression initializer;
    private IdentifierToken id;
    private KxiType type;

    public KxiVariableDeclaration(AbstractKxiExpression initializer, IdentifierToken id, KxiType type) {
        super(initializer, id, type);
        this.initializer = initializer;
        this.id = id;
        this.type = type;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
