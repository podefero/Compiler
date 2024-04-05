package compilers.visitor.kxi.symboltable;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MethodScope extends BlockScope{
    private SymbolData returnType;
    private List<SymbolData> params;
}
