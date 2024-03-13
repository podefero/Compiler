package compilers.ast.kxi_nodes.scope;

import compilers.ast.kxi_nodes.KxiIntCase;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;

import java.util.List;

public class KxiCaseBlockInt extends AbstractKxiCaseBlock<KxiIntCase>{

    public KxiCaseBlockInt(List<KxiIntCase> cases, List<AbstractKxiStatement> statements) {
        super(cases, statements);
    }
}
