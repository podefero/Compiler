package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Data;
import lombok.Getter;

@Data
public class KxiVariableDeclaration extends AbstractKxiNode {
    private AbstractKxiExpression initializer;
    private IdentifierToken id;
    private KxiAbstractType type;
    private boolean partOfDataMember;

    public KxiVariableDeclaration(AbstractKxiExpression initializer, IdentifierToken id, KxiAbstractType type) {
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
