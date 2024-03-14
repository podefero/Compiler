package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.AbstractKxiCaseBlock;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiSwitchStatement extends AbstractKxiStatement{
    private AbstractKxiCaseBlock caseBlock;
    private AbstractKxiExpression expression;
}
