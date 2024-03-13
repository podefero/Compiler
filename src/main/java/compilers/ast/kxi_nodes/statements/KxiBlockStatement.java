package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.scope.KxiBlock;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiBlockStatement extends AbstractKxiStatement {
    private KxiBlock block;
}
