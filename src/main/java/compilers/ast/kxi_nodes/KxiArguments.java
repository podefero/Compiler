package compilers.ast.kxi_nodes;

import compilers.ast.AbstractKxiNode;
import compilers.ast.ScalarType;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiArguments extends AbstractKxiNode {
    private List<AbstractKxiExpression> expressionList;
}
