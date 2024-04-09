package compilers.visitor.kxi.invalid_break;

import compilers.ast.kxi_nodes.statements.KxiBreakStatement;
import compilers.exceptions.InvalidBreakException;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.symboltable.BlockScope;
import compilers.visitor.kxi.symboltable.ScopeType;

public class InvalidBreakVisitor extends KxiVisitorBase {

    @Override
    public void visit(KxiBreakStatement breakStatement) {
        if (currentScope instanceof BlockScope) {
            if (((BlockScope) currentScope).getScopeType() != ScopeType.If
                    && ((BlockScope) currentScope).getScopeType() != ScopeType.While
                    && ((BlockScope) currentScope).getScopeType() != ScopeType.For) {
                exceptionStack.push(new InvalidBreakException(breakStatement.getLineInfo(),
                        "break can only be used in If, While or For block"));
            }
        } else {
            exceptionStack.push(new InvalidBreakException(breakStatement.getLineInfo(),
                    "Invalid break. can only be used in If, While or For block"));
        }
    }
}
