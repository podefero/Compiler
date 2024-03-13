package compilers.ast.kxi_nodes.scope;

import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractKxiCaseBlock<T> extends AbstractKxiScope{
    protected List<T> cases;
    protected List<AbstractKxiStatement> statements;
}
