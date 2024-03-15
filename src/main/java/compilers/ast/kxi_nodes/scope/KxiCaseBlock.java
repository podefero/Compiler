package compilers.ast.kxi_nodes.scope;

import compilers.ast.kxi_nodes.KxiCase;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public  class KxiCaseBlock extends AbstractKxiScope{
    protected List<AbstractKxiStatement> defaultStatements;
    protected List<KxiCase> cases;
}
