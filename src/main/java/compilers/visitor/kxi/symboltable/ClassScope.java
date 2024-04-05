package compilers.visitor.kxi.symboltable;

import compilers.ast.kxi_nodes.ScalarType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ClassScope extends SymbolTable {
    private String classId;
    private ScalarType scalarType;

    //includes constructor
    private Map<String, MethodScope> methodScopeMap;

    public ClassScope() {
        super();
        methodScopeMap = new HashMap<>();
        scalarType = ScalarType.ID;
    }

}
