package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.KxiTypeHelper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiNewExpressionIndex extends AbstractKxiExpression{
    private AbstractKxiExpression index;
    private KxiTypeHelper type;
}
