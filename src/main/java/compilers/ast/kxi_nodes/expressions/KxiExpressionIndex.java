package compilers.ast.kxi_nodes.expressions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiExpressionIndex extends AbstractKxiExpression{
    private AbstractKxiExpression index;
    private AbstractKxiExpression expression;
}
