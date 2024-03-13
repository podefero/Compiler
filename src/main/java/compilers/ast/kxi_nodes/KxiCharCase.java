package compilers.ast.kxi_nodes;

import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.token_expression.CharLitToken;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiCharCase extends AbstractKxiNode {
    private List<AbstractKxiStatement> statements;
    private CharLitToken charLitToken;
}
