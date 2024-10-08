package compilers.visitor.kxi.symboltable;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ScopeHandler {
    private Map<String, ClassScope> classScopeMap;
    private GlobalScope globalScope;
    private SymbolTable lasIdentified;

    public ScopeHandler() {
        classScopeMap = new HashMap<>();
    }

    public ClassScope getClassScope(String id) {
        return classScopeMap.get(id);
    }

    public SymbolData Identify(SymbolTable symbolTable, String id) {
        ClassScope classScope = bubbleToClassScope(symbolTable);
        String uniqueName = symbolTable.getUniqueName();
        if (classScope != null) {
             uniqueName = bubbleToClassScope(symbolTable).getUniqueName();
        }
        if (id.equals("main")) return globalScope.getMainScope().getReturnType();

        while (symbolTable != null) {
            lasIdentified = symbolTable;
            SymbolData symbolData;
            if (symbolTable instanceof GlobalScope)
                symbolData = symbolTable.getScope().get(uniqueName + id);
            else
                symbolData = symbolTable.getScope().get(id);

            if (symbolData != null) return symbolData;

            symbolTable = symbolTable.parent;
        }
        return null;
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

    public MethodScope bubbleToMethodScope(SymbolTable symbolTable, String id) {
        if (id.equals("main")) return globalScope.getMainScope();
        //traverse up nested scope
        while (symbolTable != null) {
            if (symbolTable instanceof ClassScope) {
                MethodScope methodScope;
                methodScope = ((ClassScope) symbolTable).getMethodScopeMap().get(id);
                if (methodScope != null) return methodScope;
            }
            symbolTable = symbolTable.parent;
        }

        return null;
    }

    public MethodScope bubbleToNearestMethodScope(SymbolTable symbolTable) {
        //traverse up nested scope
        while (symbolTable != null) {
            if (symbolTable instanceof BlockScope) {
                if (((BlockScope) symbolTable).scopeType == ScopeType.Main) {
                    return globalScope.getMainScope();
                }
                if (((BlockScope) symbolTable).scopeType == ScopeType.Method || ((BlockScope) symbolTable).scopeType == ScopeType.Constructor) {
                    ClassScope classScope = bubbleToClassScope(symbolTable);
                    if (classScope != null) {
                        return classScope.getMethodScopeMap().get(((BlockScope) symbolTable).getMethodId());
                    } else {
                        return null;
                    }
                }
            }
            symbolTable = symbolTable.parent;
        }

        return null;
    }

    public boolean isIdClass(String id) {
        return false;
    }

    public void addClassScope(String id, ClassScope classScope) {
        classScopeMap.put(id, classScope);
    }

}
