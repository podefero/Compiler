package compilers.ast.kxi_nodes.scope;

import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiBlock extends AbstractKxiScope{
    private List<AbstractKxiStatement> statements;
}
