package compilers.visitor.intermediate;

import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterLit;
import compilers.ast.intermediate.InterPtr;
import compilers.ast.intermediate.statements.InterGlobalVariable;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.token_literals.IntLitToken;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.symboltable.ClassScope;
import compilers.visitor.kxi.symboltable.SymbolData;
import compilers.visitor.kxi.symboltable.SymbolTable;

import java.util.Stack;

public class ExpressionToTempVisitor extends KxiVisitorBase {
    Stack<ExpressionLiteral> litStack;

    public ExpressionToTempVisitor() {
        litStack = new Stack<>();
    }


    @Override
    public void visit(ExpressionBoolLit node) {
        if (node.getTokenLiteral().getValue() == true)
            litStack.push(new ExpressionIntLit(new IntLitToken("1")));
        else
            litStack.push(new ExpressionIntLit(new IntLitToken("-1")));
    }

    @Override
    public void visit(ExpressionCharLit node) {
        litStack.push(node);
    }

    @Override
    public void visit(ExpressionIdLit node) {

    }

    @Override
    public void visit(ExpressionIntLit node) {
        nodeStack.push(new InterLit<>(node.getTokenLiteral().getValue(), ScalarType.INT));
    }

    @Override
    public void visit(ExpressionNullLit node) {
        nodeStack.push(new InterLit<>(0, ScalarType.INT));
    }

    @Override
    public void visit(ExpressionStringLit node) {
        InterPtr interId = new InterPtr(ScalarType.STRING);
        InterGlobalVariable interGlobalVariable = new InterGlobalVariable(interId, Directive.STR
                , new InterLit(node.getTokenLiteral().getValue(), ScalarType.STRING));
        nodeStack.push(interId);
        globalVariables.add(interGlobalVariable);
    }

    @Override
    public void visit(KxiPlus node) {
    }
    @Override
    public void visit(KxiPlus node) {
    }
    @Override
    public void visit(KxiPlus node) {
    }
}
