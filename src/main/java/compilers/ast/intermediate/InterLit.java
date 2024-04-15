package compilers.ast.intermediate;

import compilers.ast.kxi_nodes.ScalarType;
import lombok.Getter;

@Getter
public class InterLit<T> extends InterValue {
    private T value;
    private ScalarType scalarType;

    public InterLit(T value, ScalarType scalarType) {
        this.value = value;
        this.scalarType = scalarType;
    }

}
