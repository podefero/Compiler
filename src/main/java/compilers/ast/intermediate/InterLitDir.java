package compilers.ast.intermediate;

import compilers.ast.assembly.Directive;
import compilers.ast.kxi_nodes.ScalarType;
import lombok.Getter;

@Getter
public class InterLitDir<T> extends InterValue {
    private T value;
    private ScalarType scalarType;
    Directive directive;

    public InterLitDir(T value, ScalarType scalarType, Directive directive) {
        this.value = value;
        this.scalarType = scalarType;
        this.directive = directive;
    }


    @Override
    public String getTerminalValue() {
        return value.toString();
    }
}
