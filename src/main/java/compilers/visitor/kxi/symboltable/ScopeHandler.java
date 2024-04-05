package compilers.visitor.kxi.symboltable;

import lombok.Setter;

import java.util.Map;

@Setter
public class ScopeHandler {
    private Map<String, ClassScope> classScopeMap;
    private GlobalScope globalScope;

    public ClassScope getClassScope(String id) {
        return null;
    }

    public SymbolData Identify(SymbolTable symbolTable, String id) {
        return null;
    }

    public MethodScope getMethodScope(String classId, String methodId) {
        return null;
    }

    public ClassScope bubbleToClassScope(SymbolTable symbolTable) {
        return null;
    }

    public boolean isIdClass(String id) {
        return false;
    }

    public GlobalScope getGlobalScope() {
        return globalScope;
    }
}
