package compilers.visitor.kxi.symboltable;

import lombok.Getter;

@Getter
public class GlobalScope extends SymbolTable {
    private MainScope mainScope;
}
