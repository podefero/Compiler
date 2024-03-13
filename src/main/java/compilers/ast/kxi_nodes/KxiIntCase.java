package compilers.ast.kxi_nodes;

import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.token_expression.IntLitToken;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiIntCase extends AbstractKxiNode {
    private List<AbstractKxiStatement> statements;
    private IntLitToken intLitToken;

}
