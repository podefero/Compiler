package compilers.visitor.kxi.symboltable;

import compilers.ast.kxi_nodes.KxiAbstractType;
import compilers.ast.kxi_nodes.Modifier;
import compilers.ast.kxi_nodes.ScalarType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SymbolData {
    private boolean isStatic;
    private Modifier modifier;
    private KxiAbstractType type;

    public ScalarType getScalarType() {
        return type.getScalarType();
    }
}
