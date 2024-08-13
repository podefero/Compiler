package compilers.ast.kxi_nodes.expressions.literals;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.token_literals.TokenLiteral;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ExpressionLiteral<T extends TokenLiteral> extends AbstractKxiExpression {
    T tokenLiteral;
    protected boolean left;
    public ExpressionLiteral(T tokenLiteral) {
        super(tokenLiteral);
        this.tokenLiteral  = tokenLiteral;
    }
}
