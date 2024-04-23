package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KxiParameter extends AbstractKxiNode {
    private IdentifierToken idToken;
    private KxiAbstractType type;
    String id;

    public KxiParameter(IdentifierToken idToken, KxiAbstractType type) {
        super(idToken);
        this.idToken = idToken;
        this.type = type;
        this.id = idToken.getValue();
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visit.visit(this);
    }
}
