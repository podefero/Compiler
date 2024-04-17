package compilers.visitor.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.GenericNode;
import compilers.ast.intermediate.*;
import compilers.ast.intermediate.InterOperand.*;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.*;
import compilers.ast.intermediate.statements.*;
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
import compilers.visitor.kxi.symboltable.SymbolTable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Getter
public class KxiToIntermediateVisitor extends KxiVisitorBase {
    private final Stack<AbstractInterNode> nodeStack;
    private InterGlobal rootNode;
    private Stack<InterStatement> rightToLeftStack;
    private InterFunctionNode currentFunction;


    public KxiToIntermediateVisitor() {
        nodeStack = new Stack<>();
        rightToLeftStack = new Stack<>();
    }

    private void addStatementToFunc(InterStatement interStatement) {
        currentFunction.getStatements().add(interStatement);
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

        InterId interId = new InterId(node.getId().getValue());
        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, new GenericListNode(new ArrayList<>()));
        currentFunction = interFunctionNode;

        interFunctionNode.getStatements().add(new InterActivationRecord(interId));


        InterGlobal interGlobal = new InterGlobal(globalDir, globalInit, functions, new InterFunctionalCall(interId, new GenericListNode(new ArrayList<>())));
        rootNode = interGlobal;

        rootNode.getInterFunctionNode().add(interFunctionNode);
    }

    @Override
    public void visit(KxiMain kxiMain) {
//        InterId interId = rootNode.getInterFunctionNode().get(0).getInterId();
//        rootNode.getInterFunctionNode().get(0).getStatements().add(0,new InterFunctionalCall(interId, new GenericListNode(new ArrayList<>())));
    }

    @Override
    public void preVisit(KxiMethod node) {
        InterId interId = new InterId(node.getId().getValue());
        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, new GenericListNode(new ArrayList<>()));
        currentFunction = interFunctionNode;
        interFunctionNode.getStatements().add(new InterActivationRecord(interId));
        rootNode.getInterFunctionNode().add(interFunctionNode);
    }

    @Override
    public void visit(KxiMethod node) {

    }

    private InterOperand getLeftOperand() {
        InterValue left = pop();
        InterOperand leftOperand;

        if (left instanceof InterId)
            leftOperand = new LeftVariableStack(left);
        else
            leftOperand = new LeftOperandLit(left);

        return leftOperand;
    }

    private InterOperand getRightOperand() {
        InterValue right = pop();
        InterOperand rightOperand;

        if (right instanceof InterId)
            rightOperand = new RightVariableStack(right);
        else
            rightOperand = new RightOperandLit(right);

        return rightOperand;
    }

    private void tempVariableMaker(int hash, InterOperation interOperation) {
        //create temp variable
        InterId tempId = new InterId(hash);
        InterVariable interVariable = new InterVariable(tempId, interOperation);
        addStatementToFunc(interVariable);
        nodeStack.push(tempId);
    }


    @Override
    public void visit(KxiReturnStatement node) {
        InterReturn interReturn = new InterReturn();
        currentFunction.getStatements().add(interReturn);
    }

    @Override
    public void visit(KxiDiv node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterBinaryDivide(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiMult node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterBinaryMult(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiPlus node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterBinaryPlus(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiSubtract node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterBinarySubtract(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiEquals node) {
        if (nodeStack.size() >= 2) {
            InterOperand rightOperand = getRightOperand();
            InterOperand leftOperand = getLeftOperand();
            InterExpressionStatement interExpressionStatement = new InterExpressionStatement(new InterAssignment(leftOperand, rightOperand));

            currentFunction.getStatements().add(interExpressionStatement);
            nodeStack.push(rightOperand.getInterValue());
        }
    }

    @Override
    public void visitStatement(AbstractKxiStatement abstractKxiStatement) {
        while (!rightToLeftStack.empty()) addStatementToFunc(rightToLeftStack.pop());
    }

    @Override
    public void visit(KxiDivEquals node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();

        InterBinaryDivide interBinaryPlus = new InterBinaryDivide((InterOperand) leftOperand.copy(), (InterOperand) rightOperand.copy());
        InterId tempId = new InterId(interBinaryPlus.hashCode());
        InterVariable interVariable = new InterVariable(tempId, interBinaryPlus);

        RightVariableStack rightVar = new RightVariableStack(tempId);
        InterAssignment interAssignment = new InterAssignment((InterOperand) leftOperand.copy(), rightVar);

        nodeStack.push(rightOperand.getInterValue());
        rightToLeftStack.push(new InterBinaryAssignmentStatement(interAssignment, interVariable));
    }

    @Override
    public void visit(KxiMultEquals node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();

        InterBinaryMult interBinaryPlus = new InterBinaryMult((InterOperand) leftOperand.copy(), (InterOperand) rightOperand.copy());
        InterId tempId = new InterId(interBinaryPlus.hashCode());
        InterVariable interVariable = new InterVariable(tempId, interBinaryPlus);

        RightVariableStack rightVar = new RightVariableStack(tempId);
        InterAssignment interAssignment = new InterAssignment((InterOperand) leftOperand.copy(), rightVar);

        nodeStack.push(rightOperand.getInterValue());
        rightToLeftStack.push(new InterBinaryAssignmentStatement(interAssignment, interVariable));
    }


    @Override
    public void visit(KxiPlusEquals node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();

        InterBinaryPlus interBinaryPlus = new InterBinaryPlus((InterOperand) leftOperand.copy(), (InterOperand) rightOperand.copy());
        InterId tempId = new InterId(interBinaryPlus.hashCode());
        InterVariable interVariable = new InterVariable(tempId, interBinaryPlus);

        RightVariableStack rightVar = new RightVariableStack(tempId);
        InterAssignment interAssignment = new InterAssignment((InterOperand) leftOperand.copy(), rightVar);

        nodeStack.push(rightOperand.getInterValue());
        rightToLeftStack.push(new InterBinaryAssignmentStatement(interAssignment, interVariable));
    }

    @Override
    public void visit(KxiSubtractEquals node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();

        InterBinarySubtract interBinaryPlus = new InterBinarySubtract((InterOperand) leftOperand.copy(), (InterOperand) rightOperand.copy());
        InterId tempId = new InterId(interBinaryPlus.hashCode());
        InterVariable interVariable = new InterVariable(tempId, interBinaryPlus);

        RightVariableStack rightVar = new RightVariableStack(tempId);
        InterAssignment interAssignment = new InterAssignment((InterOperand) leftOperand.copy(), rightVar);

        nodeStack.push(rightOperand.getInterValue());
        rightToLeftStack.push(new InterBinaryAssignmentStatement(interAssignment, interVariable));
    }

    @Override
    public void visit(KxiAnd node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterLogicalAnd(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiEqualsEquals node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterLogicalEqualsEquals(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiGreaterEqualsThen node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterLogicalGreaterEqualThen(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiGreaterThen node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterLogicalGreaterThen(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiLessEqualsThen node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterLogicalLessEqualThen(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiLessThen node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterLogicalLessThen(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiNotEquals node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterLogicalNotEquals(leftOperand, rightOperand));
    }

    @Override
    public void visit(KxiOr node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterLogicalOr(leftOperand, rightOperand));
    }

    @Override
    public void visit(ExpressionBoolLit node) {
        if (node.getTokenLiteral().getValue() == true)
            nodeStack.push(new InterLit<>(1, ScalarType.INT));
        else
            nodeStack.push(new InterLit<>(0, ScalarType.INT));


    }

    @Override
    public void visit(ExpressionCharLit node) {
    }

    @Override
    public void visit(ExpressionIdLit node) {
        nodeStack.push(new InterId(node.getTokenLiteral().getValue()));
    }

    @Override
    public void visit(ExpressionIntLit node) {
        nodeStack.push(new InterLit<>(node.getTokenLiteral().getValue(), ScalarType.INT));
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
        InterOperand rightOperand = getRightOperand();
        //InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(node.hashCode(), new InterLogicalNot(new LeftOperandLit(new InterLit<>(-1, ScalarType.INT)), rightOperand));
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
        InterCoutStatement interCoutStatement;
        interCoutStatement = new InterCoutStatement(node.getScalarType(), getRightOperand());
        currentFunction.getStatements().add(interCoutStatement);
    }


    @Override
    public void visit(KxiSwitchStatement node) {
    }

    @Override
    public void visit(KxiVariableDeclaration node) {
        InterId varId = new InterId(node.getId().getValue());
        LeftVariableStack leftVariableStack = new LeftVariableStack(varId);

        InterVariable interVariable;
        if (node.getInitializer() != null) {
            interVariable = new InterVariable(varId, new InterAssignment(leftVariableStack, getRightOperand()));
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
