package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.KxiType;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiNewExpressionArgument extends AbstractKxiExpression{
    private List<AbstractKxiExpression> expressionList;
    private KxiType type;
}
