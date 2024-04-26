package compilers.visitor.intermediate;

import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.ast.kxi_nodes.statements.KxiBreakStatement;
import compilers.ast.kxi_nodes.statements.KxiReturnStatement;
import compilers.ast.kxi_nodes.statements.KxiSwitchStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.symboltable.BlockScope;
import compilers.visitor.kxi.symboltable.ScopeType;

import java.util.List;
import java.util.Stack;

public class BreakAndReturnsVisitor extends KxiVisitorBase {

    Stack<String> breakLoopStack;

    public BreakAndReturnsVisitor() {
        breakLoopStack = new Stack<>();
    }


    private boolean hasNode(Class<? extends AbstractKxiNode> node, List<? extends AbstractKxiNode> list) {
        for (AbstractKxiNode abstractKxiNode : list) {
            if (abstractKxiNode.getClass() == node) {
                return true;
            }
        }
        return false;
    }

    private boolean areLiterals(AbstractKxiNode... nodes) {

        for (AbstractKxiNode node : nodes) {
            if (!(node instanceof ExpressionLiteral)) {
                return false;
            }
        }
        return true;

    }

    @Override
    public void preVisit(KxiSwitchStatement node) {
        breakLoopStack.push(node.getExitLoop());
    }

    @Override
    public void preVisit(KxiWhileStatement node) {
        breakLoopStack.push(node.getExitLoop());
    }

    @Override
    public void preVisit(KxiForStatement node) {
        breakLoopStack.push(node.getExitLoop());
    }

    @Override
    public void visit(KxiSwitchStatement node) {
        breakLoopStack.pop();
    }

    @Override
    public void visit(KxiWhileStatement node) {
        breakLoopStack.pop();
    }

    @Override
    public void visit(KxiForStatement node) {
        breakLoopStack.pop();
    }


    @Override
    public void visit(KxiBreakStatement node) {
        node.setExitLoop(breakLoopStack.peek());
    }

}
