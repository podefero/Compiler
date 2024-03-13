package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;

import java.util.Optional;

public class KxiForStatement extends AbstractKxiConditionalStatement {
    private Optional<AbstractKxiExpression> preExpression;
    private Optional<AbstractKxiExpression> postExpression;

    public KxiForStatement(AbstractKxiConditionalStatement conditionalExpression, AbstractKxiStatement statement, Optional<AbstractKxiExpression> preExpression, Optional<AbstractKxiExpression> postExpression) {
        super(conditionalExpression, statement);
        this.preExpression = preExpression;
        this.postExpression = postExpression;
    }
}
