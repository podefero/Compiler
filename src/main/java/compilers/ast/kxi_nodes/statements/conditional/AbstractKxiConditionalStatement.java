package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractKxiConditionalStatement extends AbstractKxiStatement {
    protected AbstractKxiConditionalStatement conditionalExpression;
    protected AbstractKxiStatement statement;
}
