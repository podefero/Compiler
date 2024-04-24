package compilers.visitor.intermediate;

import compilers.ast.intermediate.statements.InterVariable;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.token_literals.IntLitToken;
import compilers.visitor.kxi.KxiVisitorBase;

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
        litStack.push(node);
    }

    @Override
    public void visit(ExpressionIntLit node) {
        litStack.push(node);
    }

    @Override
    public void visit(ExpressionNullLit node) {
        litStack.push(node);
    }

    @Override
    public void visit(ExpressionStringLit node) {
       litStack.push(node);
    }
    //TODO scripts
    //TODO Fix If For While statements, blocks literally {}
    //TODO Fix precedence grammar

//    @Override
//    public void visit(KxiPlus node) {
//        InterVariable interVariable =
//    }
//    @Override
//    public void visit(KxiPlus node) {
//    }
//    @Override
//    public void visit(KxiPlus node) {
//    }
}
