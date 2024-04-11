package compilers.visitor.kxi.invalid_break;

import compilers.ast.kxi_nodes.statements.KxiBreakStatement;
import compilers.exceptions.InvalidBreakException;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.symboltable.BlockScope;
import compilers.visitor.kxi.symboltable.ScopeType;
import compilers.visitor.kxi.symboltable.SymbolTable;

public class InvalidBreakVisitor extends KxiVisitorBase {

    @Override
    public void dumpErrorStack() {
        System.out.println("Invalid Break Errors");
        super.dumpErrorStack();
    }

    @Override
    public void visit(KxiBreakStatement breakStatement) {
        SymbolTable scope = currentScope;
        boolean found = false;
        while (scope != null) {
            if (scope instanceof BlockScope) {
                if (((BlockScope) scope).getScopeType() == ScopeType.Switch
                        || ((BlockScope) scope).getScopeType() == ScopeType.While
                        || ((BlockScope) scope).getScopeType() == ScopeType.For) {
                    found = true;
                    break;
                }
            }
            scope = scope.getParent();
        }

        if (!found)
            exceptionStack.push(new InvalidBreakException(breakStatement.getLineInfo(),
                    "break can only be used in Switch, While or For block"));
    }
}
