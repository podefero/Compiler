package compilers.ast.kxi_nodes.expressions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiParenthExpression extends AbstractKxiExpression{
    private AbstractKxiExpression expression;
}
