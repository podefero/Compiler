package compilers.visitor.kxi.symboltable;

import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.Modifier;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
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

    public SymbolData getClassData() {
        KxiType kxiType = new KxiType(scalarType, new IdentifierToken(classId));
        SymbolData symbolData = new SymbolData(false, Modifier.PUBLIC, kxiType);
        return symbolData;
    }
}
