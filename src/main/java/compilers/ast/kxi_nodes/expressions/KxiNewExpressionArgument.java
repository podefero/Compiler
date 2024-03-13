package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.KxiType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiNewExpressionArgument extends AbstractKxiExpression{
    private KxiType type;
    private KxiExpressionArguments arguments;
}
