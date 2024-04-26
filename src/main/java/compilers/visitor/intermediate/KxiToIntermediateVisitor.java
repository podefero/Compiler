//package compilers.visitor.intermediate;
//
//import compilers.ast.GenericListNode;
//import compilers.ast.GenericNode;
//import compilers.ast.assembly.Directive;
//import compilers.ast.intermediate.*;
//import compilers.ast.intermediate.expression.operation.*;
//import compilers.ast.intermediate.statements.*;
//import compilers.ast.kxi_nodes.*;
//import compilers.ast.kxi_nodes.class_members.KxiDataMember;
//import compilers.ast.kxi_nodes.class_members.KxiMethod;
//import compilers.ast.kxi_nodes.expressions.literals.*;
//import compilers.visitor.kxi.KxiVisitorBase;
//import compilers.visitor.kxi.symboltable.*;
//import lombok.Getter;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Stack;
//
//@Getter
//public class KxiToIntermediateVisitor extends KxiVisitorBase {
//    private final Stack<AbstractInterNode> nodeStack;
//    private List<InterGlobalVariable> globalVariables;
//    private List<InterOperation> globalInit;
//    private List<InterFunctionNode> functions;
//    private ScopeHandler scopeHandler;
//
//
//    public KxiToIntermediateVisitor(ScopeHandler scopeHandler) {
//        nodeStack = new Stack<>();
//        this.scopeHandler = scopeHandler;
//        this.globalVariables = new ArrayList<>();
//        this.functions = new ArrayList<>();
//        this.globalInit = new ArrayList<>();
//    }
//
//    public <T extends GenericNode> T pop() {
//        return (T) nodeStack.pop();
//    }
//
//    public <T extends GenericNode> T popList(int size) {
//        List<T> list = new ArrayList<>();
//
//        for (int i = 0; i < size; i++) {
//            list.add(pop());
//        }
//        Collections.reverse(list);
//        GenericListNode genericListNode = new GenericListNode(list);
//
//        return (T) genericListNode;
//    }
//
//    @Override
//    public void visit(KxiMain node) {
//        InterFunctionNode interFunctionNode = new InterFunctionNode(new InterId(node.getId(), ScalarType.VOID)
//                , new GenericListNode(node.getBlock().getInterBlock().getInterStatementList())
//                , new ArrayList<>());
//        functions.add(interFunctionNode);
//    }
//
//    @Override
//    public void visit(KxiMethod node) {
//        //get parameters for activation record
//        int paramSize = node.getParameters().size();
//        ClassScope classScope = scopeHandler.bubbleToClassScope(currentScope);
//        MethodScope methodScope = null;
//        if (classScope != null)
//            methodScope = classScope.getMethodScopeMap().get(node.getIdToken().getValue());
//
//        InterId interId = new InterId(node.getId(), node.getReturnType().getScalarType());
//        List<String> params = new ArrayList<>();
//
//        for (int i = 0; i < paramSize; i++) {
//            if (methodScope != null)
//                params.add(methodScope.getBlockScope().getUniqueName() + node.getParameters().get(i).getIdToken().getValue());
//        }
//
//        InterFunctionNode interFunctionNode = new InterFunctionNode(interId, new GenericListNode(node.getBlock().getInterBlock().getInterStatementList()), params);
//        functions.add(interFunctionNode);
//    }
//
//    private String getFullyQualifiedName(String id) {
//        if (currentScope == null) return scopeHandler.getGlobalScope().getUniqueName();
//        return currentScope.getUniqueName() + id;
//    }
//
//    @Override
//    public void visit(ExpressionStringLit node) {
//        globalVariables.add(node.getGlobalVariable());
//    }
//
//    private Directive getDirective(ScalarType scalarType) {
//        Directive directive;
//        if (scalarType == ScalarType.INT || scalarType == ScalarType.BOOL || scalarType == ScalarType.NULL) {
//            directive = Directive.INT;
//        } else if (scalarType == ScalarType.CHAR) directive = Directive.BYT;
//        else directive = Directive.STR;
//        return directive;
//    }
//
//
//    @Override
//    public void visit(KxiDataMember node) {
//        if (node.isStatic()) {
//            ScalarType scalarType = node.getVariableDeclaration().getType().getScalarType();
//            InterPtr interPtr = new InterPtr(getFullyQualifiedName(node.getVariableDeclaration().getId()), scalarType);
//            Directive directive;
//            InterGlobalVariable interGlobalVariable;
//
//            directive = getDirective(scalarType);
//
//            interGlobalVariable = new InterGlobalVariable(interPtr, directive, null);
//            globalVariables.add(interGlobalVariable);
//
//            if (node.getVariableDeclaration().getInitializer() != null) {
//                globalInit.add(node.getVariableDeclaration().getInterVariable().getInterOperation());
//            }
//
//        }
//    }
//
//}
