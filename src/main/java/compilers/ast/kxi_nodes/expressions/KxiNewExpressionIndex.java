package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.KxiType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiNewExpressionIndex extends AbstractKxiExpression{
    private KxiExpressionIndex index;
    private KxiType type;
}
