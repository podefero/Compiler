package compilers.visitor.kxi.invalid_write;

import compilers.ast.kxi_nodes.KxiArguments;
import compilers.ast.kxi_nodes.KxiIndex;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.KxiDotExpression;
import compilers.ast.kxi_nodes.expressions.KxiMethodExpression;
import compilers.ast.kxi_nodes.expressions.KxiNewExpressionArgument;
import compilers.ast.kxi_nodes.expressions.binary.assignment.AbstractBinaryAssignmentExpression;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.ast.kxi_nodes.statements.KxiExpressionStatement;
import compilers.exceptions.InvalidWriteException;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.result.ResultFlag;
import compilers.visitor.kxi.symboltable.ScopeHandler;
import compilers.visitor.kxi.symboltable.SymbolData;
import lombok.Getter;

import java.util.Stack;

@Getter
public class InvalidWriteVisitor extends KxiVisitorBase {
    private Stack<ResultFlag> resultFlagStack;
    private ScopeHandler scopeHandler;
    private int countStack;

    public InvalidWriteVisitor(Stack<ResultFlag> resultFlagStack, ScopeHandler scopeHandler) {
        this.resultFlagStack = resultFlagStack;
        this.scopeHandler = scopeHandler;
        countStack = 0;
    }


    private ResultFlag pop() {
        return resultFlagStack.pop();
    }

    private void update(ResultFlag resultFlag) {
        pop();
        resultFlagStack.push(resultFlag);
    }


    private void removeFlag(ResultFlag... flags) {
        if (resultFlagStack.empty()) return;
        ResultFlag flag = resultFlagStack.peek();
        for (ResultFlag f : flags) {
            if (flag == f) {
                pop();
                return;
            }
        }
    }

    private void flagCheck(String lineInfo, String message, ResultFlag flag) {
        if (flag != null)
            exceptionStack.push(new InvalidWriteException(lineInfo, message + " " + flag));
    }

    private ResultFlag hasFlag(ResultFlag... flags) {
        if (flags.length == 0 && !resultFlagStack.empty()) return pop();
        for (ResultFlag flag : flags) {
            if (resultFlagStack.contains(flag)) return flag;
        }
        return null;
    }

    @Override
    public void visit(ExpressionIdLit expression) {
        String id = expression.getTokenLiteral().getValue();
        if (scopeHandler.getClassScope(id) != null) {
            resultFlagStack.push(ResultFlag.Class);
        }
        resultFlagStack.push(ResultFlag.ID);
    }

    @Override
    public void visit(ExpressionNullLit expression) {
        resultFlagStack.push(ResultFlag.Keyword);
    }

    @Override
    public void visit(ExpressionThisLit expression) {
        resultFlagStack.push(ResultFlag.This);
    }

    @Override
    public void visit(ExpressionBoolLit expressionLiteral) {
        resultFlagStack.push(ResultFlag.Literal);
    }

    @Override
    public void visit(ExpressionCharLit expressionLiteral) {
        resultFlagStack.push(ResultFlag.Literal);
    }

    @Override
    public void visit(ExpressionIntLit expressionLiteral) {
        resultFlagStack.push(ResultFlag.Literal);
    }

    @Override
    public void visit(ExpressionStringLit expressionLiteral) {
        resultFlagStack.push(ResultFlag.Literal);
    }

    @Override
    public void visitAssignment(AbstractBinaryAssignmentExpression assignmentExpression) {
        ResultFlag right = pop(); //remove what's on right hand side
        flagCheck(assignmentExpression.getLineInfo(), "Can't write to", hasFlag(
                ResultFlag.Method
                , ResultFlag.Class
                , ResultFlag.Keyword
                , ResultFlag.Literal
                , ResultFlag.New
        ));
        resultFlagStack.clear();
        resultFlagStack.push(right);
    }

    @Override
    public void visitStatement(AbstractKxiStatement abstractKxiStatement) {
        resultFlagStack.clear();
    }

    @Override
    public void visit(KxiDotExpression expression) {
        removeFlag(ResultFlag.Method, ResultFlag.Class, ResultFlag.This);
    }

    @Override
    public void visit(KxiNewExpressionArgument expression) {
        resultFlagStack.push(ResultFlag.New);
    }

    @Override
    public void visit(KxiMethodExpression expression) {
        for (int i = 0; i < countStack; i++) pop();
        resultFlagStack.push(ResultFlag.Method);
    }

    @Override
    public void preVisit(KxiArguments expression) {
        countStack = resultFlagStack.size();
    }

    @Override
    public void visit(KxiArguments expression) {
        int newSize = resultFlagStack.size();
        countStack = newSize - countStack;
    }

    @Override
    public void visit(KxiIndex index) {
        pop();
    }
}
