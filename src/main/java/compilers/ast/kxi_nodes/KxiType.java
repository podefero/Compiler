package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class KxiType extends AbstractKxiNode {
    private ScalarType scalarType;
    private int arrayDimensions;
    private IdentifierToken idName;

    public KxiType(ScalarType scalarType, int arrayDimensions, IdentifierToken idName) {
        super(idName);
        this.scalarType = scalarType;
        this.arrayDimensions = arrayDimensions;
        this.idName = idName;

    }
}
