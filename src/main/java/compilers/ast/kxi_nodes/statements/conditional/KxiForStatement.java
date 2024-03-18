package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.binary.conditional.AbstractBinaryConditionalExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;

import java.util.Optional;

public class KxiForStatement extends AbstractKxiConditionalStatement {
    private Optional<AbstractKxiExpression> postExpression;
    private Optional<AbstractKxiExpression> preExpression;

    public KxiForStatement(AbstractKxiStatement statement, Optional<AbstractKxiExpression> postExpression, AbstractKxiExpression conditionalExpression,  Optional<AbstractKxiExpression> preExpression ) {
        super(statement, conditionalExpression);
        this.preExpression = preExpression;
        this.postExpression = postExpression;
    }
}
