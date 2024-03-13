package compilers.ast.kxi_nodes;

import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiMain extends AbstractKxiNode {
    private IdentifierToken id;
    private List<KxiClass> classList;
    private KxiBlock block;
}
