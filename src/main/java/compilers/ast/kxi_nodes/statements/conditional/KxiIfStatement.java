package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;

import java.util.Optional;

public class KxiIfStatement extends AbstractKxiConditionalStatement {
    private Optional<AbstractKxiStatement> elseStatement;

    public KxiIfStatement(AbstractKxiConditionalStatement conditionalExpression, AbstractKxiStatement statement, Optional<AbstractKxiStatement> elseStatement) {
        super(conditionalExpression, statement);
        this.elseStatement = elseStatement;
    }
}
