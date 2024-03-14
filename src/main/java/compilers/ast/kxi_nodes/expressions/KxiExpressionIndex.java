package compilers.ast.kxi_nodes.expressions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiExpressionIndex extends AbstractKxiExpression{
    private AbstractKxiExpression expression;
    private KxiExpressionIndex expressionIndex;
}
