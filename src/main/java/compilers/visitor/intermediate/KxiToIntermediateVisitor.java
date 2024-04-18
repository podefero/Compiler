package compilers.visitor.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.GenericNode;
import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.*;
import compilers.ast.intermediate.InterOperand.*;
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
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.symboltable.ScopeHandler;
import compilers.visitor.kxi.symboltable.SymbolData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Getter
public class KxiToIntermediateVisitor extends KxiVisitorBase {
    private final Stack<AbstractInterNode> nodeStack;
    private InterGlobal rootNode;
    private List<InterStatement> scopeBlock;
    private Stack<InterStatement> rightToLeftStack;
    private ScopeHandler scopeHandler;


    public KxiToIntermediateVisitor(ScopeHandler scopeHandler) {
        nodeStack = new Stack<>();
        rightToLeftStack = new Stack<>();
        this.scopeHandler = scopeHandler;
        scopeBlock = new ArrayList<>();
    }

    private void addStatementToCurrentScope(InterStatement interStatement) {
        currentScope.getInterStatementList().add(interStatement);
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


    }

    @Override
    public void visit(KxiMain node) {
        GenericListNode functions = new GenericListNode(new ArrayList<>());
        GenericListNode globalDir = new GenericListNode(new ArrayList<>()); //after symbol table
        GenericListNode globalInit = new GenericListNode(new ArrayList<>());
        InterId interId = new InterId(getFullyQualifiedName(node.getId().getValue()), ScalarType.VOID);
        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, new GenericListNode(scopeBlock));

        InterGlobal interGlobal =
                new InterGlobal(globalDir, globalInit, functions, new InterFunctionalCall(interId, new GenericListNode(new ArrayList<>())));
        rootNode = interGlobal;

        rootNode.getInterFunctionNode().add(interFunctionNode);

//        InterId interId = rootNode.getInterFunctionNode().get(0).getInterId();
//        rootNode.getInterFunctionNode().get(0).getStatements().add(0,new InterFunctionalCall(interId, new GenericListNode(new ArrayList<>())));
    }

    @Override
    public void preVisit(KxiMethod node) {
        InterId interId = new InterId(getFullyQualifiedName(node.getId().getValue()), node.getReturnType().getScalarType());
        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, new GenericListNode(new ArrayList<>()));
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
        else if (left instanceof InterPtr)
            leftOperand = new LeftPtr(left);
        else
            leftOperand = new LeftOperandLit(left);

        return leftOperand;
    }

    private InterOperand getRightOperand() {
        InterValue right = pop();
        InterOperand rightOperand;

        if (right instanceof InterId)
            rightOperand = new RightVariableStack(right);
        else if (right instanceof InterPtr)
            rightOperand = new RightPtr(right);
        else
            rightOperand = new RightOperandLit(right);

        return rightOperand;
    }

    private void tempVariableMaker(InterOperation interOperation, ScalarType scalarType) {
        //create temp variable
        InterId tempId = new InterId(scalarType);
        InterVariable interVariable = new InterVariable(tempId, interOperation);
        addStatementToCurrentScope(interVariable);
        nodeStack.push(tempId);
    }

    private void tempVariableMakerOnRight(InterOperation interOperation, ScalarType scalarType) {
        //create temp variable
        InterId tempId = new InterId(scalarType);

        interOperation.getRightOperand().setInterValue(tempId);
        InterVariable interVariable = new InterVariable(tempId, interOperation);
        addStatementToCurrentScope(interVariable);
        nodeStack.push(tempId);
    }

    private void tempVariableMakerOnLeft(InterOperation interOperation, ScalarType scalarType) {
        //create temp variable
        InterId tempId = new InterId(scalarType);
        interOperation.setLeftOperand(new LeftVariableStack(tempId));
        InterVariable interVariable = new InterVariable(tempId, interOperation);
        addStatementToCurrentScope(interVariable);
        nodeStack.push(tempId);
    }


    @Override
    public void visit(KxiReturnStatement node) {
        InterReturn interReturn = new InterReturn();
        addStatementToCurrentScope(interReturn);
    }

    @Override
    public void visit(KxiDiv node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterBinaryDivide(leftOperand, rightOperand), ScalarType.INT);
    }

    @Override
    public void visit(KxiMult node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterBinaryMult(leftOperand, rightOperand), ScalarType.INT);
    }

    @Override
    public void visit(KxiPlus node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterBinaryPlus(leftOperand, rightOperand), ScalarType.INT);
    }

    @Override
    public void visit(KxiSubtract node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterBinarySubtract(leftOperand, rightOperand), ScalarType.INT);
    }

    @Override
    public void visit(KxiEquals node) {
        if (nodeStack.size() >= 2) {
            InterOperand rightOperand = getRightOperand();
            InterOperand leftOperand = getLeftOperand();
            InterExpressionStatement interExpressionStatement = new InterExpressionStatement(new InterAssignment(leftOperand, rightOperand));

            addStatementToCurrentScope(interExpressionStatement);
            nodeStack.push(rightOperand.getInterValue());
        }
    }

    @Override
    public void visitStatement(AbstractKxiStatement abstractKxiStatement) {
        while (!rightToLeftStack.empty()) addStatementToCurrentScope(rightToLeftStack.pop());
    }

    @Override
    public void visit(KxiDivEquals node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();

        InterBinaryDivide interBinaryPlus = new InterBinaryDivide((InterOperand) leftOperand.copy(), (InterOperand) rightOperand.copy());
        InterId tempId = new InterId(ScalarType.INT);
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
        InterId tempId = new InterId(ScalarType.INT);
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
        InterId tempId = new InterId(ScalarType.INT);
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
        InterId tempId = new InterId(ScalarType.INT);
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
        tempVariableMaker(new InterLogicalAnd(leftOperand, rightOperand), leftOperand.getInterValue().getScalarType());
    }

    @Override
    public void visit(KxiEqualsEquals node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterLogicalEqualsEquals(leftOperand, rightOperand), leftOperand.getInterValue().getScalarType());
    }

    @Override
    public void visit(KxiGreaterEqualsThen node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterLogicalGreaterEqualThen(leftOperand, rightOperand), leftOperand.getInterValue().getScalarType());
    }

    @Override
    public void visit(KxiGreaterThen node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterLogicalGreaterThen(leftOperand, rightOperand), leftOperand.getInterValue().getScalarType());
    }

    @Override
    public void visit(KxiLessEqualsThen node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterLogicalLessEqualThen(leftOperand, rightOperand), leftOperand.getInterValue().getScalarType());
    }

    @Override
    public void visit(KxiLessThen node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterLogicalLessThen(leftOperand, rightOperand), leftOperand.getInterValue().getScalarType());
    }

    @Override
    public void visit(KxiNotEquals node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterLogicalNotEquals(leftOperand, rightOperand), leftOperand.getInterValue().getScalarType());
    }

    @Override
    public void visit(KxiOr node) {
        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterLogicalOr(leftOperand, rightOperand), leftOperand.getInterValue().getScalarType());
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
        nodeStack.push(new InterLit<>(node.getTokenLiteral().getValue(), ScalarType.CHAR));
    }

    private String getFullyQualifiedName(String id) {
        if (currentScope == null) return scopeHandler.getGlobalScope().getUniqueName();
        return currentScope.getUniqueName() + id;
    }

    @Override
    public void visit(ExpressionIdLit node) {
        SymbolData symbolData = scopeHandler.Identify(currentScope, node.getTokenLiteral().getValue());
        ScalarType scalarType = symbolData.getScalarType();
//        boolean isStatic = symbolData.isStatic();
//        if(scalarType == ScalarType.STRING || isStatic)
//            nodeStack.push(new InterPtr(getFullyQualifiedName(node.getTokenLiteral().getValue()), scalarType));
//        else
        nodeStack.push(new InterId(getFullyQualifiedName(node.getTokenLiteral().getValue()), scalarType));
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
        InterPtr interId = new InterPtr(ScalarType.STRING);
        InterGlobalVariable interGlobalVariable = new InterGlobalVariable(interId, Directive.STR
                , new InterLit(node.getTokenLiteral().getValue(), ScalarType.STRING));
        nodeStack.push(interId);
        rootNode.getGlobalInit().add(interGlobalVariable);
    }

    @Override
    public void visit(ExpressionThisLit node) {
    }

    @Override
    public void visit(KxiNot node) {
        InterOperand rightOperand = getRightOperand();
        //InterOperand leftOperand = getLeftOperand();
        tempVariableMaker(new InterLogicalNot(new LeftOperandLit(new InterLit<>(-1, ScalarType.INT)), rightOperand)
                , rightOperand.getInterValue().getScalarType());
    }

    @Override
    public void visit(KxiUniPlus node) {
        InterOperand rightOperand = getRightOperand();
        tempVariableMaker(new InterEmptyOperator(null, null)
                , ScalarType.INT);
    }

    @Override
    public void visit(KxiUniSubtract node) {
        InterOperand rightOperand = getRightOperand();
        tempVariableMakerOnLeft(new InterUnarySubOperator(null, rightOperand)
                , ScalarType.INT);
    }

    @Override
    public void visit(KxiDotExpression node) {
    }

    @Override
    public void visit(KxiMethodExpression node) {
    }

    @Override
    public void visit(KxiBlock node) {
        scopeBlock = node.getScope().getInterStatementList();
        super.visit(node);
    }

    @Override
    public void visit(KxiForStatement node) {
    }


    @Override
    public void preVisit(KxiIfStatement node) {
    }

    @Override
    public void visit(KxiIfStatement node) {
        GenericListNode genericListNode = new GenericListNode(scopeBlock);
        InterIfStatement interIfStatement = new InterIfStatement(pop(), genericListNode);
        addStatementToCurrentScope(interIfStatement);
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
        InterOperand rightOperand = getRightOperand();
        interCoutStatement = new InterCoutStatement(rightOperand.getInterValue().getScalarType(), rightOperand);
        addStatementToCurrentScope(interCoutStatement);
    }


    @Override
    public void visit(KxiSwitchStatement node) {
    }

    @Override
    public void visit(KxiVariableDeclaration node) {
        InterId varId = new InterId(getFullyQualifiedName(node.getId().getValue()), node.getType().getScalarType());

        //Stack Var
        LeftVariableStack leftVariableStack = new LeftVariableStack(varId);

        InterVariable interVariable;
        if (node.getInitializer() != null) {
            InterOperand rightOperand = getRightOperand();
            if (rightOperand instanceof RightPtr)
                interVariable = new InterVariable(varId, new InterPtrAssignment(leftVariableStack, rightOperand));
            else
                interVariable = new InterVariable(varId, new InterAssignment(leftVariableStack, rightOperand));
        } else {
            interVariable = new InterVariable(varId, null);
        }
        addStatementToCurrentScope(interVariable);
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
