package compilers.visitor.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.GenericNode;
import compilers.ast.intermediate.*;
import compilers.ast.intermediate.InterOperand.*;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.InterAssignment;
import compilers.ast.intermediate.expression.operation.InterBinaryPlus;
import compilers.ast.intermediate.statements.InterExpressionStatement;
import compilers.ast.intermediate.statements.InterReturn;
import compilers.ast.intermediate.statements.InterVariable;
import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.class_members.KxiConstructor;
import compilers.ast.kxi_nodes.class_members.KxiDataMember;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.KxiDotExpression;
import compilers.ast.kxi_nodes.expressions.KxiMethodExpression;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniPlus;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Getter
public class KxiToIntermediateVisitor extends KxiVisitorBase {
    private final Stack<AbstractInterNode> nodeStack;
    private InterGlobal rootNode;
    private List<InterExpression> expressionsList;
    private InterFunctionNode currentFunction;


    public KxiToIntermediateVisitor() {
        nodeStack = new Stack<>();
        expressionsList = new ArrayList<>();
    }

    private GenericListNode getExpressionList() {
        GenericListNode genericListNode = new GenericListNode(List.copyOf(expressionsList));
        expressionsList.clear();
        return genericListNode;
    }

    public <T extends GenericNode> T pop() {
        return (T) nodeStack.pop();
    }

    public <T extends GenericNode> T popList(int size) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(pop());
        }
        Collections.reverse(list);
        GenericListNode genericListNode = new GenericListNode(list);

        return (T) genericListNode;
    }

    @Override
    public void preVisit(KxiMain node) {
        GenericListNode functions = new GenericListNode(new ArrayList<>());
        GenericListNode globalDir = new GenericListNode(new ArrayList<>()); //after symbol table
        GenericListNode globalInit = new GenericListNode(new ArrayList<>());

        InterGlobal interGlobal = new InterGlobal(globalDir, globalInit, functions);
        rootNode = interGlobal;

        InterId interId = new InterId(node.getId().getValue());
        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, new GenericListNode(new ArrayList<>()));
        currentFunction = interFunctionNode;
        rootNode.getInterFunctionNode().add(interFunctionNode);
    }


    @Override
    public void preVisit(KxiMethod node) {
        InterId interId = new InterId(node.getId().getValue());
        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, new GenericListNode(new ArrayList<>()));
        currentFunction = interFunctionNode;
        rootNode.getInterFunctionNode().add(interFunctionNode);
    }

    @Override
    public void visit(KxiMethod node) {

    }

    @Override
    public void visit(KxiReturnStatement node) {
        InterReturn interReturn = new InterReturn();
        currentFunction.getStatements().add(interReturn);
    }

    @Override
    public void visit(KxiDiv node) {
    }

    @Override
    public void visit(KxiMult node) {
    }

    @Override
    public void visit(KxiPlus node) {
        InterValue left = pop();
        InterValue right = pop();

        InterOperand leftOperand;
        InterOperand rightOperand;

        if (left instanceof InterId)
            leftOperand = new LeftVariableStack(left);
        else
            leftOperand = new LeftOperandLit(left);

        if (right instanceof InterId)
            rightOperand = new RightVariableStack(left);
        else
            rightOperand = new RightOperandLit(left);


        InterId tempId = new InterId(node.hashCode());
        InterVariable interVariable = new InterVariable(tempId, new InterBinaryPlus(leftOperand, rightOperand));
        currentFunction.getStatements().add(interVariable);
        nodeStack.push(tempId);
    }


    @Override
    public void visit(KxiSubtract node) {
    }

    @Override
    public void visit(KxiDivEquals node) {
    }

    @Override
    public void visit(KxiEquals node) {
        if (nodeStack.size() == 2) {
            InterExpressionStatement interExpressionStatement = new InterExpressionStatement(new InterAssignment(pop(), pop()));
            currentFunction.getStatements().add(interExpressionStatement);
        }
    }

    @Override
    public void visit(KxiMultEquals node) {
    }

    @Override
    public void visit(KxiPlusEquals node) {
    }

    @Override
    public void visit(KxiSubtractEquals node) {
    }

    @Override
    public void visit(KxiAnd node) {
    }

    @Override
    public void visit(KxiEqualsEquals node) {
    }

    @Override
    public void visit(KxiGreaterEqualsThen node) {
    }

    @Override
    public void visit(KxiGreaterThen node) {
    }

    @Override
    public void visit(KxiLessEqualsThen node) {
    }

    @Override
    public void visit(KxiLessThen node) {
    }

    @Override
    public void visit(KxiNotEquals node) {
    }

    @Override
    public void visit(KxiOr node) {
    }

    @Override
    public void visit(ExpressionBoolLit node) {
    }

    @Override
    public void visit(ExpressionCharLit node) {
    }

    @Override
    public void visit(ExpressionIdLit node) {
    }

    @Override
    public void visit(ExpressionIntLit node) {
        nodeStack.push(new InterLit<>(node.getTokenLiteral().getValue()));
    }

    @Override
    public void visit(ExpressionNullLit node) {
    }

    @Override
    public void visit(ExpressionStringLit node) {
    }

    @Override
    public void visit(ExpressionThisLit node) {
    }

    @Override
    public void visit(KxiNot node) {
    }

    @Override
    public void visit(KxiUniPlus node) {
    }

    @Override
    public void visit(KxiUniSubtract node) {
    }

    @Override
    public void visit(KxiDotExpression node) {
    }

    @Override
    public void visit(KxiMethodExpression node) {
    }

    @Override
    public void visit(KxiForStatement node) {
    }

    @Override
    public void visit(KxiIfStatement node) {
    }

    @Override
    public void visit(KxiWhileStatement node) {
    }

    @Override
    public void visit(KxiBreakStatement node) {
    }

    @Override
    public void visit(KxiCinStatement node) {
    }

    @Override
    public void visit(KxiCoutStatement node) {
    }

    @Override
    public void visit(KxiExpressionStatement node) {
    }

    @Override
    public void visit(KxiSwitchStatement node) {
    }

    @Override
    public void visit(KxiVariableDeclaration node) {
        InterId varId = new InterId(node.getId().getValue());
        LeftVariableStack leftVariableStack = new LeftVariableStack(varId);
        InterOperand rightOperand;

        InterValue right = pop();

        if (right instanceof InterId)
            rightOperand = new RightVariableStack(right);
        else
            rightOperand = new RightOperandLit(right);


        InterVariable interVariable;
        if (node.getInitializer() != null) {
            interVariable = new InterVariable(varId, new InterAssignment(leftVariableStack, rightOperand));
        } else {
            interVariable = new InterVariable(varId, null);
        }
        currentFunction.getStatements().add(interVariable);


    }

    @Override
    public void visit(KxiArguments node) {
    }

    @Override
    public void visit(KxiParameter node) {
    }

    @Override
    public void visit(KxiCaseChar node) {
    }

    @Override
    public void visit(KxiCaseInt node) {
    }

    @Override
    public void visit(KxiConstructor node) {
    }

    @Override
    public void visit(KxiDataMember node) {
    }

}
