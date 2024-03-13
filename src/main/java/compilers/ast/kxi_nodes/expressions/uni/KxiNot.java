package compilers.ast.kxi_nodes.expressions.uni;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;

public class KxiNot extends AbstractKxiUniOperation{
    public KxiNot(AbstractKxiExpression expression) {
        super(expression);
    }
}
