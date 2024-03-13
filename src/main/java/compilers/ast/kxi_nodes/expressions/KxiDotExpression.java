package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiDotExpression extends AbstractKxiExpression{
    private AbstractKxiExpression expression;
    private IdentifierToken id;
}
