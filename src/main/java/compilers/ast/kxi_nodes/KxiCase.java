package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiCase<T> extends AbstractKxiNode {
    private List<AbstractKxiStatement> statements;
    private T caseValue;
}
