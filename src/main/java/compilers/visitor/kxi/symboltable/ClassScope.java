package compilers.visitor.kxi.symboltable;

import lombok.Data;

import java.util.Map;

@Data
public class ClassScope extends SymbolTable {
    private SymbolData classType;
    //includes constructor
    private Map<String, MethodScope> methodScopeMap;
}
