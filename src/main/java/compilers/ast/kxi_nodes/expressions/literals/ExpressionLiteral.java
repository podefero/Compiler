package compilers.ast.kxi_nodes.expressions.literals;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.token_literals.TokenLiteral;

public abstract class ExpressionLiteral<T extends TokenLiteral> extends AbstractKxiExpression {
    T tokenLiteral;
    public ExpressionLiteral(T tokenLiteral) {
        super(tokenLiteral);
        this.tokenLiteral  = tokenLiteral;
    }
}
