package compilers.ast.kxi_nodes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiTypeHelper extends AbstractKxiNode {
    private ScalarType scalarType;
    private int arrayDimensions;
}
