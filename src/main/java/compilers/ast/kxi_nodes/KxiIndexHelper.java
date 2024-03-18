package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiIndexHelper extends AbstractKxiNode {
    private AbstractKxiExpression expression;
}
