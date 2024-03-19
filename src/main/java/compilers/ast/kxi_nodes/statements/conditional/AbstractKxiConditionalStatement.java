package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractKxiConditionalStatement extends AbstractKxiStatement {
    protected AbstractKxiStatement statement;
    protected AbstractKxiExpression conditionalExpression;

}
