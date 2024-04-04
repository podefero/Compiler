package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import lombok.Getter;

@Getter
public class KxiType extends KxiAbstractType {

    private IdentifierToken idName;
    public KxiType(ScalarType scalarType, IdentifierToken idName) {
        super(scalarType);
        this.idName = idName;
    }

}
