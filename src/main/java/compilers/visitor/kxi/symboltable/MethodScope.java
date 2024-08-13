package compilers.visitor.kxi.symboltable;

import lombok.Getter;

import java.util.List;

@Getter
public class MethodScope  {
    private SymbolData returnType;
    private List<SymbolData> params;
    private BlockScope blockScope;

    public MethodScope(SymbolData returnType, List<SymbolData> params, BlockScope blockScope) {
        this.returnType = returnType;
        this.params = params;
        this.blockScope = blockScope;
    }

}
