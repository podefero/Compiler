package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.ast.kxi_nodes.statements.KxiBlockStatement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractKxiConditionalStatement extends AbstractKxiStatement {
    protected AbstractKxiStatement statement;
    protected AbstractKxiExpression conditionalExpression;

    public AbstractKxiConditionalStatement(GenericNode... genericNodes) {
        super(genericNodes);
    }

}
