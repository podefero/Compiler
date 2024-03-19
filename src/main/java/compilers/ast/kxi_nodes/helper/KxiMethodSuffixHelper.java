package compilers.ast.kxi_nodes.helper;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KxiMethodSuffixHelper extends AbstractKxiNode {
    private KxiBlock block;
    private GenericListNode parameters;
    private IdentifierToken id;

}
