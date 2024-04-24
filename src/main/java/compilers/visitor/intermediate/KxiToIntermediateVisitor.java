package compilers.visitor.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.GenericNode;
import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.*;
import compilers.ast.intermediate.InterOperand.*;
import compilers.ast.intermediate.expression.InterVariable;
import compilers.ast.intermediate.expression.operation.*;
import compilers.ast.intermediate.statements.*;
import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.class_members.KxiConstructor;
import compilers.ast.kxi_nodes.class_members.KxiDataMember;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.KxiDotExpression;
import compilers.ast.kxi_nodes.expressions.KxiMethodExpression;
import compilers.ast.kxi_nodes.expressions.KxiPostForExpression;
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
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
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
    private List<InterGlobalVariable> globalVariables;
    private List<InterOperation> globalInit;
    private List<InterFunctionNode> globalFunctions;
    private ScopeHandler scopeHandler;


    public KxiToIntermediateVisitor(ScopeHandler scopeHandler) {
        nodeStack = new Stack<>();
        this.scopeHandler = scopeHandler;
        this.globalVariables = new ArrayList<>();
        this.globalFunctions = new ArrayList<>();
        this.globalInit = new ArrayList<>();
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
        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, node.getBlock().getInterBlock(), new ArrayList<>());

        InterGlobal interGlobal =
                new InterGlobal(globalDir, globalInit, functions, new InterFunctionalCall(interId, ScalarType.VOID));
        rootNode = interGlobal;

        rootNode.getInterFunctionNode().add(0, interFunctionNode);
    }

    @Override
    public void visit(KxiMethod node) {
        //get parameters for activation record
        int paramSize = node.getParameters().size();
        ClassScope classScope = scopeHandler.bubbleToClassScope(currentScope);
        MethodScope methodScope = null;
        if (classScope != null)
            methodScope = classScope.getMethodScopeMap().get(node.getIdToken().getValue());

        InterId interId = new InterId(getFullyQualifiedName(node.getIdToken().getValue()), node.getReturnType().getScalarType());
        List<String> params = new ArrayList<>();

        for (int i = 0; i < paramSize; i++) {
            if (methodScope != null)
                params.add(methodScope.getBlockScope().getUniqueName() + node.getParameters().get(i).getIdToken().getValue());
        }

        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, node.getBlock().getInterBlock(), params);
        globalFunctions.add(interFunctionNode);
    }

    private String getFullyQualifiedName(String id) {
        if (currentScope == null) return scopeHandler.getGlobalScope().getUniqueName();
        return currentScope.getUniqueName() + id;
    }

    @Override
    public void visit(ExpressionStringLit node) {
        globalVariables.add(node.getGlobalVariable());
    }

    private Directive getDirective(ScalarType scalarType) {
        Directive directive;
        if (scalarType == ScalarType.INT || scalarType == ScalarType.BOOL || scalarType == ScalarType.NULL) {
            directive = Directive.INT;
        } else if (scalarType == ScalarType.CHAR) directive = Directive.BYT;
        else directive = Directive.STR;
        return directive;
    }


    @Override
    public void visit(KxiDataMember node) {
        if (node.isStatic()) {
            ScalarType scalarType = node.getVariableDeclaration().getType().getScalarType();
            InterPtr interPtr = new InterPtr(getFullyQualifiedName(node.getVariableDeclaration().getId()), scalarType);
            Directive directive;
            InterGlobalVariable interGlobalVariable;

            directive = getDirective(scalarType);

            interGlobalVariable = new InterGlobalVariable(interPtr, directive, null);
            globalVariables.add(interGlobalVariable);

            if (node.getVariableDeclaration().getInitializer() != null) {
                globalInit.add(node.getVariableDeclaration().getInterVariable().getInterOperation());
            }

        }
    }

}
