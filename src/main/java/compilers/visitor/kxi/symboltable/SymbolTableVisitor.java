package compilers.visitor.kxi.symboltable;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.Stack;

@Getter
public class SymbolTableVisitor extends KxiVisitorBase {
    private ScopeHandler scopeHandler;
    private Stack<SymbolTable> tableStack;
    private GlobalScope globalScope;
    private SymbolTable currentSymbolTable;

    public SymbolTableVisitor() {
        super();
        scopeHandler = new ScopeHandler();
        tableStack = new Stack<>();
        globalScope = new GlobalScope();
    }


}
