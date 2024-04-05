package compilers.visitor.kxi.symboltable;

import java.util.List;

public class MainScope extends MethodScope{
    public MainScope(SymbolData returnType, List<SymbolData> params) {
        super(returnType, params);
    }
}
