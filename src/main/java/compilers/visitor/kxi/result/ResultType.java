package compilers.visitor.kxi.result;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.symboltable.SymbolData;
import compilers.visitor.kxi.symboltable.SymbolTable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultType extends KxiResult {
    private String referenceId;
    private SymbolData typeData;
    private SymbolTable scope;

    public String getTypeDatId() {
        return typeData.getType().getKxiType().getIdName().getValue();
    }

    public ScalarType getScalarType() {
        return typeData.getType().getScalarType();
    }

}
