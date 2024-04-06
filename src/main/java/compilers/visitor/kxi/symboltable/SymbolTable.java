package compilers.visitor.kxi.symboltable;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class SymbolTable {
    protected Map<String, SymbolData> scope;
    protected SymbolTable parent;
    protected String uniqueName;

    public SymbolTable() {
        scope = new HashMap<>();
        uniqueName = "";
    }

    public void setUniqueName(String name) {
        this.uniqueName = name + "$";
    }
}
