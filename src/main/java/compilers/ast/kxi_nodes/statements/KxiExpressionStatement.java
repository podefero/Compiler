package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiExpressionStatement extends AbstractKxiStatement{
    AbstractKxiExpression expression;
}
