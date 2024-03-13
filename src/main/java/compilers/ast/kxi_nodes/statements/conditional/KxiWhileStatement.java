package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;

public class KxiWhileStatement extends AbstractKxiConditionalStatement{
    public KxiWhileStatement(AbstractKxiConditionalStatement conditionalExpression, AbstractKxiStatement statement) {
        super(conditionalExpression, statement);
    }
}
