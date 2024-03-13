package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiCoutStatement extends AbstractKxiStatement{
    private AbstractKxiExpression expression;

}
