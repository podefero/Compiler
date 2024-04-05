package compilers.visitor.kxi.symboltable;

import lombok.Data;

@Data
public class GlobalScope extends SymbolTable {
    private BlockScope mainScope;
}
