package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.binary.conditional.AbstractBinaryConditionalExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;

import java.util.Optional;

public class KxiIfStatement extends AbstractKxiConditionalStatement {
    private Optional<AbstractKxiStatement> elseStatement;


    public KxiIfStatement(Optional<AbstractKxiStatement> elseStatement, AbstractKxiStatement statement, AbstractBinaryConditionalExpression conditionalExpression) {
        super(statement, conditionalExpression);
        this.elseStatement = elseStatement;
    }
}
