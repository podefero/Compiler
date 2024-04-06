package compilers.visitor.kxi.symboltable;

import java.util.HashMap;
import java.util.Map;

public class ScopeHandler {
    private Map<String, ClassScope> classScopeMap;
    private GlobalScope globalScope;

    public ScopeHandler() {
        classScopeMap = new HashMap<>();
    }

    public ClassScope getClassScope(String id) {
        return classScopeMap.get(id);
    }

    public SymbolData Identify(SymbolTable symbolTable, String id) {
        return symbolTable.getScope().get(id);
    }

    public MethodScope getMethodScope(String classId, String methodId) {
        return null;
    }

    public ClassScope bubbleToClassScope(SymbolTable symbolTable) {
        //check current scope
        if (symbolTable instanceof ClassScope) return (ClassScope) symbolTable;

        //otherwise traverse up nested scope
        while (!(symbolTable instanceof GlobalScope)) {
            symbolTable = symbolTable.parent;
            if (symbolTable instanceof ClassScope)
                return (ClassScope) symbolTable;
        }

        return null;
    }

    public boolean isIdClass(String id) {
        return false;
    }

    public GlobalScope getGlobalScope() {
        return globalScope;
    }

    public void addClassScope(String id, ClassScope classScope) {
        classScopeMap.put(id, classScope);
    }

}
