package compilers.ast.kxi_nodes;

import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.expressions.token_expression.TokenType;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class KxiVariableDeclaration extends AbstractKxiNode {
    private Optional<AbstractKxiExpression> expressionHuh;
    private IdentifierToken id;
    private KxiType type;
}
