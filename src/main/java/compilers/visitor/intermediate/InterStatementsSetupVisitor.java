package compilers.visitor.intermediate;

import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.kxi_nodes.KxiArguments;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.expressions.KxiPostForExpression;
import compilers.ast.kxi_nodes.expressions.KxiPreForExpression;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniPlus;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.visitor.kxi.KxiVisitorBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InterStatementsSetupVisitor extends KxiVisitorBase {
    Stack<InterExpression> interExpressionStack;

    public InterStatementsSetupVisitor() {
        interExpressionStack = new Stack<>();
    }

    private void pushExpression(InterExpression interExpression) {
        interExpressionStack.push(interExpression);
    }

    private List<InterExpression> dumpExpressions() {
        List<InterExpression> interExpressions = new ArrayList<>();
        while (!interExpressionStack.empty()) {
            interExpressions.add(interExpressionStack.pop());
        }
        return interExpressions;
    }


    @Override
    public void visit(KxiIfStatement node) {
        node.setInterExpressions(dumpExpressions());
    }

    @Override
    public void visit(KxiWhileStatement node) {
        node.setInterExpressions(dumpExpressions());
    }

    @Override
    public void visit(KxiPreForExpression node) {
        node.setInterExpressions(dumpExpressions());
    }

    @Override
    public void visit(KxiPostForExpression node) {
        node.setInterExpressions(dumpExpressions());
    }

    @Override
    public void visit(KxiForStatement node) {
        node.setInterExpressions(dumpExpressions());
    }

    @Override
    public void visit(KxiReturnStatement node) {
        node.setInterExpressions(dumpExpressions());
    }

    @Override
    public void visit(KxiCoutStatement node) {
        node.setInterExpressions(dumpExpressions());
    }

    @Override
    public void visit(KxiCinStatement node) {
        node.setInterExpressions(dumpExpressions());
    }

    @Override
    public void visit(KxiSwitchStatement node) {
        node.setInterExpressions(dumpExpressions());
    }

    @Override
    public void visit(KxiExpressionStatement node) {
        node.setInterExpressions(dumpExpressions());
    }

    public void visit(KxiVariableDeclaration node) {
        if (node.getInitializer() != null)
            node.setInterStatements(dumpExpressions());
    }

    @Override
    public void visit(KxiMultEquals node) {
        pushExpression(node.getInterAssignment());
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiDivEquals node) {
        pushExpression(node.getInterAssignment());
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiPlusEquals node) {
        pushExpression(node.getInterAssignment());
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiSubtractEquals node) {
        pushExpression(node.getInterAssignment());
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiMult node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiDiv node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiPlus node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiSubtract node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiLessThen node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiGreaterThen node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiGreaterEqualsThen node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiLessEqualsThen node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiEqualsEquals node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiNotEquals node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiAnd node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiOr node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiNot node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiUniPlus node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiUniSubtract node) {
        pushExpression(node.getInterVariable());
    }

    @Override
    public void visit(KxiEquals node) {
        pushExpression(node.getInterVariable());
    }

}
