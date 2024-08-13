package compilers.ast.kxi_nodes;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.InterVariable;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Data;

import java.util.List;

@Data
public class KxiVariableDeclaration extends AbstractKxiNode {
    private AbstractKxiExpression initializer;
    private IdentifierToken idToken;
    private KxiAbstractType type;
    private boolean partOfDataMember;

    String id;

    public KxiVariableDeclaration(AbstractKxiExpression initializer, IdentifierToken idToken, KxiAbstractType type) {
        super(initializer, idToken, type);
        this.initializer = initializer;
        this.idToken = idToken;
        this.type = type;
        this.id = idToken.getValue();
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
