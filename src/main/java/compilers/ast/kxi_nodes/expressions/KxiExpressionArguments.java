package compilers.ast.kxi_nodes.expressions;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiExpressionArguments extends AbstractKxiExpression{
    private AbstractKxiExpression expression;
    private List<KxiExpressionArguments> argumentsList;
}
