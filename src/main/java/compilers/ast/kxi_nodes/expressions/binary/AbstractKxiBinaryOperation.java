package compilers.ast.kxi_nodes.expressions.binary;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractKxiBinaryOperation extends AbstractKxiExpression {
    protected AbstractKxiExpression expressionL;
    protected AbstractKxiExpression expressionR;
}
