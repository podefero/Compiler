package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiCinStatement extends AbstractKxiStatement{
    private AbstractKxiExpression expression;
}
