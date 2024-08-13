package compilers.visitor.intermediate;

import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.KxiParameter;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.KxiDotExpression;
import compilers.ast.kxi_nodes.expressions.literals.ExpressionIdLit;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.symboltable.ClassScope;
import compilers.visitor.kxi.symboltable.ScopeHandler;
import compilers.visitor.kxi.symboltable.SymbolData;
import compilers.visitor.kxi.symboltable.SymbolTable;

import java.util.Stack;

public class FullyLoadedIdVisitor extends KxiVisitorBase {
    ScopeHandler scopeHandler;
    Stack<ExpressionIdLit> idStack;

    public FullyLoadedIdVisitor(ScopeHandler scopeHandler) {
        idStack = new Stack<>();
        this.scopeHandler = scopeHandler;
    }

    private String getFullyQualifiedName(String id) {
        if (currentScope == null) return scopeHandler.getGlobalScope().getUniqueName();
        return currentScope.getUniqueName() + id;
    }

    private void setNodeIdLit(String newId) {
        if(!idStack.empty()) {
            ExpressionIdLit idLit = idStack.pop();
            String value = idLit.getId() + "$" + newId;
            idLit.setId(idLit.getId() + "$" + newId);
            idLit.getTokenLiteral().setValue(value);
        }
    }

    @Override
    public void visit(ExpressionIdLit node) {
        SymbolData symbolData = scopeHandler.Identify(currentScope, node.getTokenLiteral().getValue());
        if (node.getTokenLiteral().getValue().equals("main")) {
            node.setId(scopeHandler.getGlobalScope().getMainScope().getBlockScope().getUniqueName() + "main");
            node.setScalarType(ScalarType.VOID);
        } else if (symbolData != null) {
            ScalarType scalarType = symbolData.getScalarType();
            SymbolTable whatScopeIdFound = scopeHandler.getLasIdentified();
            boolean isStatic = symbolData.isStatic();
            if (isStatic) {
                ClassScope classScope = scopeHandler.bubbleToClassScope(currentScope);
                node.setId(classScope.getUniqueName() + node.getTokenLiteral().getValue());
                node.setScalarType(scalarType);
            } else {
                node.setId(whatScopeIdFound.getUniqueName() + node.getTokenLiteral().getValue());
                node.setScalarType(scalarType);
            }
        } else {
            //push for dot
            node.setId(node.getTokenLiteral().getValue());
            idStack.push(node);
        }

        node.getTokenLiteral().setValue(node.getId());
    }


    @Override
    public void visit(KxiDotExpression node) {
        setNodeIdLit(node.getId().getValue());
    }

    @Override
    public void visit(KxiMethod node) {
        node.setId(getFullyQualifiedName(node.getId()));
        node.getIdToken().setValue(node.getId());
    }

    @Override
    public void visit(KxiMain node) {
        node.setId(getFullyQualifiedName(node.getId()));
        node.getIdentifierToken().setValue(node.getId());
    }

    @Override
    public void visit(KxiVariableDeclaration node) {
        node.setId(getFullyQualifiedName(node.getId()));
        node.getIdToken().setValue(node.getId());
    }

    @Override
    public void visit(KxiParameter node) {
        node.setId(getFullyQualifiedName(node.getId()));
        node.getIdToken().setValue(node.getId());
    }
}
