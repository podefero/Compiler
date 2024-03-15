package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiSwitchStatement extends AbstractKxiStatement{
    private KxiCaseBlock caseBlock;
    private AbstractKxiExpression expression;
}
