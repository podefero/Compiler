package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiParameter extends AbstractKxiNode {
    private IdentifierToken id;
    private KxiTypeHelper type;
}
