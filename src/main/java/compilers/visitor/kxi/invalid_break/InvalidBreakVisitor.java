package compilers.visitor.kxi.invalid_break;

import compilers.ast.kxi_nodes.statements.KxiBreakStatement;
import compilers.ast.kxi_nodes.statements.KxiSwitchStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.exceptions.InvalidBreakException;
import compilers.visitor.kxi.KxiVisitorBase;

import java.util.Stack;

public class InvalidBreakVisitor extends KxiVisitorBase {

    Stack<KxiBreakStatement> breakStack;
    boolean withinContext;

    public InvalidBreakVisitor() {
        this.breakStack = new Stack<>();
        withinContext = false;
    }

    @Override
    public void dumpErrorStack() {
        //System.err.println("Invalid Break Errors");
        super.dumpErrorStack();
    }

    @Override
    public void preVisit(KxiSwitchStatement node) {
        withinContext = true;
    }

    @Override
    public void visit(KxiSwitchStatement node) {
        withinContext = false;
    }

    @Override
    public void preVisit(KxiWhileStatement node) {
        withinContext = true;
    }

    @Override
    public void visit(KxiWhileStatement node) {
        withinContext = false;
    }

    @Override
    public void preVisit(KxiForStatement node) {
        withinContext = true;
    }

    @Override
    public void visit(KxiForStatement node) {
        withinContext = false;
    }

    @Override
    public void visit(KxiBreakStatement breakStatement) {
        if (!withinContext)
            exceptionStack.push(new InvalidBreakException(breakStatement.getLineInfo(), "Invalid break statement"));
    }
}
