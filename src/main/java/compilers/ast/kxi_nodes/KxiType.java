package compilers.ast.kxi_nodes;

import compilers.ast.AbstractKxiNode;
import compilers.ast.ScalarType;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiType extends AbstractKxiNode {
    private ScalarType scalarType;
    private int arrayDimensions;
}
