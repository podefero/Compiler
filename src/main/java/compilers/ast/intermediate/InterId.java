package compilers.ast.intermediate;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.util.HashString;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InterId extends InterValue {
    private String id;
    boolean isReturn;

    public InterId(String id, ScalarType scalarType) {
        super(scalarType);
        this.id =  id;
    }

    public InterId(ScalarType scalarType) {
        super(scalarType);
        this.id =  "temp$" + HashString.updateStringHash();
    }

    @Override
    public GenericNode copy() {
        return new InterId(id, scalarType);
    }

    @Override
    public String getTerminalValue() {
        return this.id;
    }
}
