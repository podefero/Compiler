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
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
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
import compilers.ast.kxi_nodes.helper.KxiFordSemi;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiElseStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.symboltable.*;
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
    private Stack<InterStatement> rightToLeftStackGlobal;
    private List<InterStatement> caseStatements;
    private List<InterGlobalVariable> globalVariables;
    private List<InterOperation> globalInit;
    private List<InterFunctionNode> globalFunctions;
    boolean hasCase;
    private ScopeHandler scopeHandler;


    public KxiToIntermediateVisitor(ScopeHandler scopeHandler) {
        nodeStack = new Stack<>();
        rightToLeftStack = new Stack<>();
        rightToLeftStackGlobal = new Stack<>();
        this.scopeHandler = scopeHandler;
        scopeBlock = new ArrayList<>();
        caseStatements = new ArrayList<>();
        this.globalVariables = new ArrayList<>();
        this.globalFunctions = new ArrayList<>();
        this.globalInit = new ArrayList<>();
        hasCase = false;
    }

    private void addStatementToCurrentScopeFromLast(int i, InterStatement interStatement) {
        int last = currentScope.getInterStatementList().size();
        currentScope.getInterStatementList().add(last - i, interStatement);
    }

    private void addStatementToCurrentScope(InterStatement interStatement) {
        //case will siphon statements, due to not being a scope.
        if (hasCase) caseStatements.add(interStatement);
        else currentScope.getInterStatementList().add(interStatement);
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

    }

    @Override
    public void visit(KxiMain node) {
        GenericListNode functions = new GenericListNode(globalFunctions);
        GenericListNode globalDir = new GenericListNode(globalVariables); //after symbol table
        GenericListNode globalInit = new GenericListNode(this.globalInit);
        InterId interId = new InterId(getFullyQualifiedName(node.getId().getValue()), ScalarType.VOID);
        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, new GenericListNode(scopeBlock), new ArrayList<>());

        InterGlobal interGlobal =
                new InterGlobal(globalDir, globalInit, functions, new InterFunctionalCall(interId, new GenericListNode(new ArrayList<>())));
        rootNode = interGlobal;

        rootNode.getInterFunctionNode().add(0, interFunctionNode);

//        InterId interId = rootNode.getInterFunctionNode().get(0).getInterId();
//        rootNode.getInterFunctionNode().get(0).getStatements().add(0,new InterFunctionalCall(interId, new GenericListNode(new ArrayList<>())));
    }

    @Override
    public void visit(KxiMethod node) {
        //get parameters for activation record
        int paramSize = node.getParameters().size();
        ClassScope classScope = scopeHandler.bubbleToClassScope(currentScope);
        MethodScope methodScope = null;
        if (classScope != null)
            methodScope = classScope.getMethodScopeMap().get(node.getId().getValue());

        InterId interId = new InterId(getFullyQualifiedName(node.getId().getValue()), node.getReturnType().getScalarType());
        List<String> params = new ArrayList<>();

        for (int i = 0; i < paramSize; i++) {
            if (methodScope != null)
                params.add(methodScope.getBlockScope().getUniqueName() + node.getParameters().get(i).getId().getValue());
        }

        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, new GenericListNode(scopeBlock), params);
        // interFunctionNode.getStatements().add(new InterActivationRecord(interId));
        globalFunctions.add(interFunctionNode);
    }

    private InterOperand getLeftOperand() {
        InterValue left;
        if (!nodeStack.isEmpty()) left = pop();
        else return null;
        InterOperand leftOperand;

        if (left instanceof InterId)
            if (((InterId) left).isReturn()) leftOperand = new OperandReturn(left, true);
            else leftOperand = new LeftVariableStack(left);
        else if (left instanceof InterPtr)
            leftOperand = new LeftPtr(left);
        else
            leftOperand = new LeftOperandLit(left);

        return leftOperand;
    }

    private InterOperand getRightOperand(InterValue interValue) {
        InterValue right = interValue;
        InterOperand rightOperand;

        if (right instanceof InterId) {
            if (((InterId) right).isReturn()) rightOperand = new OperandReturn(right, false);
            else rightOperand = new RightVariableStack(right);
        } else if (right instanceof InterPtr)
            rightOperand = new RightPtr(right);
        else
            rightOperand = new RightOperandLit(right);

        return rightOperand;
    }

    private InterOperand getRightOperand() {
        InterValue right;
        if (!nodeStack.isEmpty()) right = pop();
        else return null;
        InterOperand rightOperand;

        if (right instanceof InterId) {
            if (((InterId) right).isReturn()) rightOperand = new OperandReturn(right, false);
            else rightOperand = new RightVariableStack(right);
        } else if (right instanceof InterPtr)
            rightOperand = new RightPtr(right);
        else
            rightOperand = new RightOperandLit(right);

        return rightOperand;
    }

    private void tempVariableMaker(InterOperation interOperation, ScalarType scalarType) {
        //if we are in a class scope than assume static
        if (currentScope instanceof ClassScope) {
            InterPtr interPtr = new InterPtr(scalarType);
            globalVariables.add(new InterGlobalVariable(interPtr, getDirective(scalarType), null));
            globalInit.add(interOperation);
            globalInit.add(new InterAssignment(new LeftPtr((InterValue) interPtr.copy()), null));
            nodeStack.push(interPtr);
        } else {
            InterId interId = new InterId(scalarType);
            InterVariable interVariable = new InterVariable(interId, interOperation);
            addStatementToCurrentScope(interVariable);
            nodeStack.push(interId);
        }


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
        InterReturn interReturn;
        InterOperand rightOperand;
        if (node.getExpression() != null) {
            InterValue interValue = getRightOperand().getInterValue();
            rightOperand = getRightOperand((InterValue) interValue.copy());
        } else
            rightOperand = new RightOperandLit(new InterLit<>(0, ScalarType.INT));

        interReturn = new InterReturn(rightOperand);
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

    private void convertToGlobalBinaryAssignmentArithmic(InterOperand leftOperand, InterOperand rightOperand, InterOperation arithmic, ScalarType scalarType) {
        InterPtr interPtr = new InterPtr(scalarType);
        Directive directive = getDirective(scalarType);

        InterGlobalVariable interGlobalVariable = new InterGlobalVariable(interPtr, directive, null);
        RightPtr rightPtr = new RightPtr(interPtr);
        InterAssignment interAssignment = new InterAssignment((InterOperand) leftOperand.copy(), rightPtr);

        globalVariables.add(interGlobalVariable);
        rightToLeftStackGlobal.push(new InterBinaryAssignmentStatementGlobal(arithmic, interAssignment));
        nodeStack.push(interPtr);
    }

    @Override
    public void visit(KxiDivEquals node) {

        InterOperand rightOperand = getRightOperand();
        InterOperand leftOperand = getLeftOperand();

        InterBinaryDivide interBinaryPlus = new InterBinaryDivide((InterOperand) leftOperand.copy(), (InterOperand) rightOperand.copy());

        if (scopeHandler.bubbleToClassScope(currentScope) != null) {
            convertToGlobalBinaryAssignmentArithmic(leftOperand, rightOperand, interBinaryPlus, ScalarType.INT);
            return;
        }

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
        if (scopeHandler.bubbleToClassScope(currentScope) != null) {
            convertToGlobalBinaryAssignmentArithmic(leftOperand, rightOperand, interBinaryPlus, ScalarType.INT);
            return;
        }
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
        if (scopeHandler.bubbleToClassScope(currentScope) != null) {
            convertToGlobalBinaryAssignmentArithmic(leftOperand, rightOperand, interBinaryPlus, ScalarType.INT);
            return;
        }
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
        if (scopeHandler.bubbleToClassScope(currentScope) != null) {
            convertToGlobalBinaryAssignmentArithmic(leftOperand, rightOperand, interBinaryPlus, ScalarType.INT);
            return;
        }
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
            nodeStack.push(new InterLit<>(-1, ScalarType.INT));
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
        if (symbolData != null) {
            ScalarType scalarType = symbolData.getScalarType();
            SymbolTable whatScopeIdFound = scopeHandler.getLasIdentified();
            boolean isStatic = symbolData.isStatic();
            if (isStatic) {
                ClassScope classScope = scopeHandler.bubbleToClassScope(currentScope);
                nodeStack.push(new InterPtr(classScope.getUniqueName() + node.getTokenLiteral().getValue(), scalarType));
            } else
                nodeStack.push(new InterId(whatScopeIdFound.getUniqueName() + node.getTokenLiteral().getValue(), scalarType));
        } else
            nodeStack.push(new InterId(node.getTokenLiteral().getValue(), ScalarType.UNKNOWN));
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
        InterId interId = pop();
        SymbolTable symbolTable = scopeHandler.getClassScope(interId.getId());
        SymbolData symbolData = scopeHandler.Identify(symbolTable, node.getId().getValue());
        String newId = symbolTable.getUniqueName() + node.getId().getValue();
        InterPtr interPtr = new InterPtr(newId, symbolData.getScalarType());
        nodeStack.push(interPtr);
    }

    @Override
    public void visit(KxiMethodExpression node) {
        //handle args
        int numArgs = node.getArguments().getArguments().size();
        Stack<InterValue> args = new Stack<>();

        //get args left to right order
        for (int i = 0; i < numArgs; i++) {
            AbstractKxiExpression expression = node.getArguments().getArguments().get(i);
            //get temp from binary assignment
            if (expression instanceof AbstractBinaryAssignmentExpression) {
                pop(); //discard
                InterBinaryAssignmentStatement interStatement = (InterBinaryAssignmentStatement) rightToLeftStack.pop();

                addStatementToCurrentScopeFromLast(i, interStatement); //just so the node is visited before others
                InterId interId = new InterId(interStatement.getInterVariable().getInterId().getId()
                        , interStatement.getInterVariable().getInterId().getScalarType());
                args.push(interId);
            } else {
                args.push(pop());
            }
        }


        InterValue interValue = pop();
        InterId interId;
        if (interValue instanceof InterPtr) {
            interId = new InterId(((InterPtr) interValue).getId(), interValue.getScalarType());
        } else interId = (InterId) interValue;

        List<InterPushArg> interPushArgs = new ArrayList<>();
        while (!args.empty()) {
            InterPushArg interPushArg = new InterPushArg(getRightOperand(args.pop()));
            interPushArgs.add(interPushArg);
        }

        InterFunctionalCall interFunctionalCall = new InterFunctionalCall(interId, new GenericListNode(interPushArgs));
        addStatementToCurrentScope(interFunctionalCall);


        addStatementToCurrentScope(new EndFunctionCall());
        interId.setReturn(true);
        nodeStack.push(interId);
    }

    @Override
    public void visit(KxiBlock node) {
        scopeBlock = node.getScope().getInterStatementList();
        super.visit(node);
    }


    @Override
    public void visit(KxiExpressionStatement node) {
        if (!nodeStack.empty()) pop(); //not sure about this yet
    }


    @Override
    public void visit(KxiElseStatement node) {
        GenericListNode genericListNode = new GenericListNode(scopeBlock);
        InterElseStatement interElseStatement = new InterElseStatement(genericListNode);
        nodeStack.push(interElseStatement);
    }

    @Override
    public void visit(KxiIfStatement node) {
        GenericListNode genericListNode = new GenericListNode(scopeBlock);
        InterElseStatement interElseStatement = null;
        InterDerefStatement interDerefStatement = new InterDerefStatement((InterOperand) getRightOperand().copy());

        if (!nodeStack.empty() && nodeStack.peek() instanceof InterElseStatement) {
            interElseStatement = pop();
        }

        InterIfStatement interIfStatement =
                new InterIfStatement(genericListNode, interElseStatement, getFullyQualifiedName(HashString.updateStringHash()));
        addStatementToCurrentScope(interDerefStatement); //ensures we have a boolean value in R2

        addStatementToCurrentScope(interIfStatement);

        if (interElseStatement != null) {
            interElseStatement.setDone(interIfStatement.getDone());
            interElseStatement.setIfNot(interIfStatement.getIfNot());
        }
    }

    @Override
    public void preVisit(KxiWhileStatement node) {
        //set up loop
        InterWhileLoop interWhileLoop = new InterWhileLoop(node.getLoopLabel());
        addStatementToCurrentScope(interWhileLoop);
    }


    @Override
    public void visit(KxiWhileStatement node) {
        GenericListNode genericListNode = new GenericListNode(scopeBlock);
        InterDerefStatement interDerefStatement = new InterDerefStatement((InterOperand) getRightOperand().copy());

        InterWhileStatement interIfStatement =
                new InterWhileStatement(genericListNode, node.getLoopLabel(), node.getExitLoop());

        addStatementToCurrentScope(interDerefStatement);
        addStatementToCurrentScope(interIfStatement);
    }

    @Override
    public void preVisit(KxiForStatement node) {
        //set up loop
        InterWhileLoop interWhileLoop = new InterWhileLoop(node.getLoopLabel());
        addStatementToCurrentScope(interWhileLoop);
    }


    @Override
    public void visit(KxiForStatement node) {
        if (node.getPostExpression() != null) pop();
        GenericListNode genericListNode = new GenericListNode(scopeBlock);
        InterDerefStatement interDerefStatement = new InterDerefStatement((InterOperand) getRightOperand().copy());

        InterWhileStatement interIfStatement =
                new InterWhileStatement(genericListNode, node.getLoopLabel(), node.getExitLoop());

        addStatementToCurrentScope(interDerefStatement);
        addStatementToCurrentScope(interIfStatement);
    }

    @Override
    public void visit(KxiBreakStatement node) {
        InterBreak interBreak = new InterBreak(node.getExitLoop());
        addStatementToCurrentScope(interBreak);
    }

    @Override
    public void visit(KxiCinStatement node) {
        InterCinStatement interCinStatement;
        InterOperand leftOperand = getLeftOperand();
        interCinStatement = new InterCinStatement(leftOperand.getInterValue().getScalarType(), new InterAssignment(leftOperand, null));
        addStatementToCurrentScope(interCinStatement);
    }

    @Override
    public void visit(KxiCoutStatement node) {
        InterCoutStatement interCoutStatement;
        InterOperand rightOperand = getRightOperand();
        interCoutStatement = new InterCoutStatement(rightOperand.getInterValue().getScalarType(), rightOperand);
        addStatementToCurrentScope(interCoutStatement);
    }

    @Override
    public void visit(KxiSwitchStatement kxiClass) {
        InterSwitch interSwitch = pop();
        InterDerefStatement interDerefStatement = new InterDerefStatement((InterOperand) getRightOperand().copy());
        addStatementToCurrentScope(interDerefStatement);
        addStatementToCurrentScope(interSwitch);

    }

    @Override
    public void visit(KxiCaseBlock node) {
        scopeBlock = node.getScope().getInterStatementList();
        super.visit(node);

        InterSwitch interSwitch;
        GenericListNode genericListNode = new GenericListNode(scopeBlock);
        interSwitch = new InterSwitch(genericListNode, node.getExitLoop());
        nodeStack.push(interSwitch);
    }

    @Override
    public void visit(KxiVariableDeclaration node) {
        if (!node.isPartOfDataMember()) {
            InterId varId = new InterId(getFullyQualifiedName(node.getId().getValue()), node.getType().getScalarType());

            //Stack Var
            LeftVariableStack leftVariableStack = new LeftVariableStack(varId);

            InterVariable interVariable;
            if (node.getInitializer() != null) {
                InterOperand rightOperand;

                //If we have assignment arithmic change order of statements. And right hand of variable
                if (!rightToLeftStack.empty()) {
                    while (!rightToLeftStack.empty()) addStatementToCurrentScope(rightToLeftStack.pop());
                    InterBinaryAssignmentStatement binEquals
                            = (InterBinaryAssignmentStatement) currentScope.getInterStatementList().get(currentScope.getInterStatementList().size() - 1);
                    rightOperand = (InterOperand) new RightVariableStack(binEquals.getInterVariable().getInterId()).copy();
                } else {
                    rightOperand = getRightOperand();
                }

                if (rightOperand instanceof RightPtr)
                    interVariable = new InterVariable(varId, new InterPtrAssignment(leftVariableStack, rightOperand));
                else
                    interVariable = new InterVariable(varId, new InterAssignment(leftVariableStack, rightOperand));
            } else {
                interVariable = new InterVariable(varId, null);
            }

            addStatementToCurrentScope(interVariable);
        }
    }

    @Override
    public void visit(KxiArguments node) {
    }

//    @Override
//    public void visit(KxiParameter node) {
//        InterId interId = new InterId(node.getId().getValue(), node.getType().getScalarType());
//        InterVariable interVariable = new InterVariable(interId, new InterEmptyOperator(null, null));
//        interVariable.setStackType(StackType.PARAM);
//        addStatementToCurrentScope(interVariable);
//    }

    @Override
    public void preVisit(KxiCaseChar node) {
        hasCase = true;
    }

    @Override
    public void preVisit(KxiCaseInt node) {
        hasCase = true;
    }

    @Override
    public void visit(KxiCaseChar node) {
        List<InterStatement> interStatements = new ArrayList<>(caseStatements);
        caseStatements.clear();
        hasCase = false;
        InterCase interCase = new InterCase(new InterLit<>(node.getCaseValue().getValue(), ScalarType.INT)
                , new GenericListNode(interStatements));
        addStatementToCurrentScope(interCase);
    }

    @Override
    public void visit(KxiCaseInt node) {
        List<InterStatement> interStatements = new ArrayList<>(caseStatements);
        caseStatements.clear();
        hasCase = false;
        InterCase interCase = new InterCase(new InterLit<>(node.getCaseValue().getValue(), ScalarType.INT)
                , new GenericListNode(interStatements));
        addStatementToCurrentScope(interCase);
    }

    @Override
    public void visit(KxiConstructor node) {
    }

    private Directive getDirective(ScalarType scalarType) {
        Directive directive;
        if (scalarType == ScalarType.INT || scalarType == ScalarType.BOOL || scalarType == ScalarType.NULL) {
            directive = Directive.INT;
        } else if (scalarType == ScalarType.CHAR) directive = Directive.BYT;
        else directive = Directive.STR;
        return directive;
    }

    InterOperation getOperationFromVariable() {
        //pop id and see if it matches from stack(list)
        InterVariable interVariable = (InterVariable) currentScope.getInterStatementList().remove(currentScope.getInterStatementList().size() - 1);
        return interVariable.getInterOperation();

    }


    @Override
    public void visit(KxiDataMember node) {
        //else it's a class record
        if (node.isStatic()) {
            ScalarType scalarType = node.getVariableDeclaration().getType().getScalarType();
            InterPtr interPtr = new InterPtr(getFullyQualifiedName(node.getVariableDeclaration().getId().getValue()), scalarType);
            Directive directive;
            InterGlobalVariable interGlobalVariable;

            directive = getDirective(scalarType);

            interGlobalVariable = new InterGlobalVariable(interPtr, directive, null);
            globalVariables.add(interGlobalVariable);

            if (node.getVariableDeclaration().getInitializer() != null) {
                InterAssignment interAssignment = new InterAssignment(new LeftPtr(interPtr), getRightOperand());
                globalInit.add(interAssignment);
            }

        }
    }

}
