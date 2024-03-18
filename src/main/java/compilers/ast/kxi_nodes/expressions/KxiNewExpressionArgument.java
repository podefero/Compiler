package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class KxiNewExpressionArgument extends AbstractKxiExpression{
    private Optional<List<AbstractKxiExpression>> arguments;
    private IdentifierToken id;
}
