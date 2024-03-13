package compilers.ast.kxi_nodes;

import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.expressions.token_expression.TokenType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiParameter extends AbstractKxiNode {
    private KxiType type;
    private IdentifierToken id;
}
