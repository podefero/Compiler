package compilers.ast.kxi_nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KxiType extends AbstractKxiNode {
    private ScalarType scalarType;
    private int arrayDimensions;
}
