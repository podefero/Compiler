package compilers.visitor.kxi.symboltable;

import compilers.ast.intermediate.statements.InterStatement;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class SymbolTable {
    protected Map<String, SymbolData> scope;
    protected SymbolTable parent;
    protected String uniqueName;
    protected List<InterStatement> interStatementList;

    public SymbolTable() {
        scope = new HashMap<>();
        uniqueName = "";
        interStatementList = new ArrayList<>();
    }

    public void setUniqueName(String name) {
        this.uniqueName = name + "$";
    }
}
