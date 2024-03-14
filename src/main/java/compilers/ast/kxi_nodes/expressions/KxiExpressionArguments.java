package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.KxiArguments;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class KxiExpressionArguments extends AbstractKxiExpression{
    private AbstractKxiExpression expression;
    private KxiArguments arguments;
}
