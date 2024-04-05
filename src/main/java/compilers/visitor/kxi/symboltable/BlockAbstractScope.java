package compilers.visitor.kxi.symboltable;

import lombok.Data;

@Data
public abstract class BlockAbstractScope extends SymbolTable {
   protected ScopeType scopeType;
}
