package compilers.visitor.kxi.symboltable;

import lombok.Data;

import java.util.Map;

@Data
public abstract class SymbolTable {
    protected Map<String, SymbolData> scope;
    protected SymbolTable parent;
}
