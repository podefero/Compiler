package compilers.ast.intermediate;

import compilers.ast.kxi_nodes.ScalarType;
import lombok.Getter;

@Getter
public class InterLitDir<T> extends InterValue {
    private T value;
    private ScalarType scalarType;

    public InterLitDir(T value, ScalarType scalarType) {
        this.value = value;
        this.scalarType = scalarType;
    }


    @Override
    public String getTerminalValue() {
        return value.toString();
    }
}
