package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KxiParameter extends AbstractKxiNode {
    private IdentifierToken id;
    private KxiType type;
}
