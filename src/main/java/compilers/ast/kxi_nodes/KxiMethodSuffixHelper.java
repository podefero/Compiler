package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class KxiMethodSuffixHelper extends AbstractKxiNode {
    private KxiBlock block;
    private Optional<List<KxiParameter>> parameters;
    private IdentifierToken id;
}
