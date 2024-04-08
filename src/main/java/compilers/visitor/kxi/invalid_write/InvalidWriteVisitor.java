package compilers.visitor.kxi.invalid_write;

import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.result.ResultFlag;
import compilers.visitor.kxi.symboltable.ScopeHandler;
import compilers.visitor.kxi.symboltable.SymbolTable;

import java.util.Stack;

public class InvalidWriteVisitor extends KxiVisitorBase {
        private Stack<ResultFlag> resultFlagStack;
        private ScopeHandler scopeHandler;
        private SymbolTable currentScope;

}
