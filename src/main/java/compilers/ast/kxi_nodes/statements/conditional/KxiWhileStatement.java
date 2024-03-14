package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.binary.conditional.AbstractBinaryConditionalExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;

public class KxiWhileStatement extends AbstractKxiConditionalStatement{

    public KxiWhileStatement(AbstractKxiStatement statement, AbstractBinaryConditionalExpression conditionalExpression) {
        super(statement, conditionalExpression);
    }
}
