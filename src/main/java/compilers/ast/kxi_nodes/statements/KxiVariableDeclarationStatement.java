package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiVariableDeclarationStatement extends AbstractKxiStatement{
    KxiVariableDeclaration variableDeclaration;
}
