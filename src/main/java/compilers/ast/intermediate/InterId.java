package compilers.ast.intermediate;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.util.HashString;
import lombok.Getter;

@Getter
public class InterId extends InterValue {
    private String id;

    public InterId(String id, ScalarType scalarType) {
        super(scalarType);
        this.id =  id;
    }

    public InterId(ScalarType scalarType) {
        super(scalarType);
        this.id =  "temp$" + HashString.updateStringHash();
    }

    @Override
    public String getTerminalValue() {
        return this.id;
    }
}
