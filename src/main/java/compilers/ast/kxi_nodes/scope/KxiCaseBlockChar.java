package compilers.ast.kxi_nodes.scope;

import compilers.ast.kxi_nodes.KxiCharCase;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import lombok.AllArgsConstructor;

import java.util.List;

public class KxiCaseBlockChar extends AbstractKxiCaseBlock<KxiCharCase>{
    public KxiCaseBlockChar(List<KxiCharCase> cases, List<AbstractKxiStatement> statements) {
        super(cases, statements);
    }
}
