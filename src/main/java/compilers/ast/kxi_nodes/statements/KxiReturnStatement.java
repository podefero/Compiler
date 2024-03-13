package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class KxiReturnStatement extends AbstractKxiStatement{
    private Optional<AbstractKxiExpression> expression;

}
