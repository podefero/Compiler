package compilers.ast.intermediate;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.ScalarType;
import lombok.Getter;

@Getter
public class InterReturnOperand extends InterValue {
    private String id;
    private ScalarType scalarType;

    public InterReturnOperand(String id, ScalarType scalarType) {
        super(scalarType);
        this.id = id;
    }

    @Override
    public GenericNode copy() {
        return new InterReturnOperand(id, scalarType);
    }

    @Override
    public String getTerminalValue() {
        return id.toString();
    }
}
