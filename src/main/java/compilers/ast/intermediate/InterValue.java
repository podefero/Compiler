package compilers.ast.intermediate;

import compilers.ast.GenericTerminal;
import compilers.ast.kxi_nodes.ScalarType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class InterValue extends AbstractInterNode implements GenericTerminal {
    protected ScalarType scalarType;
}
