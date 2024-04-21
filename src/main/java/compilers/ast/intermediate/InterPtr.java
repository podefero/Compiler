package compilers.ast.intermediate;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.util.HashString;
import lombok.Getter;

@Getter
public class InterPtr extends InterValue {
    private String id;

    public InterPtr(String id, ScalarType scalarType) {
        super(scalarType);
        this.id =  id;
    }

    public InterPtr(ScalarType scalarType) {
        super(scalarType);
        this.id =  "temp$" + HashString.updateStringHash();
    }

    @Override
    public GenericNode copy() {
        return new InterPtr(this.id, scalarType);
    }

    @Override
    public String getTerminalValue() {
        return this.id;
    }
}
