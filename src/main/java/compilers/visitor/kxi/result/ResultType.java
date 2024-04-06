package compilers.visitor.kxi.result;

import compilers.visitor.kxi.symboltable.SymbolData;
import compilers.visitor.kxi.symboltable.SymbolTable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultType extends KxiResult{
    private String referenceId;
    private SymbolData typeData;
    private SymbolTable scope;

}
