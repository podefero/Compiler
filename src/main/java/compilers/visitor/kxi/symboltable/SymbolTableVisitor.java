package compilers.visitor.kxi.symboltable;

import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.class_members.KxiConstructor;
import compilers.ast.kxi_nodes.class_members.KxiDataMember;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.ast.kxi_nodes.statements.KxiSwitchStatementChar;
import compilers.ast.kxi_nodes.statements.KxiSwitchStatementInt;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.exceptions.SymbolTableException;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Getter
public class SymbolTableVisitor extends KxiVisitorBase {
    private ScopeHandler scopeHandler;
    private Stack<SymbolTable> tableStack;
    private GlobalScope globalScope;
    private SymbolTable currentSymbolTable;

    public SymbolTableVisitor() {
        super();
        scopeHandler = new ScopeHandler();
        tableStack = new Stack<>();
        globalScope = new GlobalScope();
    }

    private void scopeNodePreVisit(SymbolTable symbolTable) {
        if (currentSymbolTable != null) {
            tableStack.push(currentSymbolTable);
        }
        currentSymbolTable = symbolTable;
    }

    private void scopeNodeVisit() {
        if (tableStack.empty()) {
            currentSymbolTable.parent = globalScope;
        } else {
            currentSymbolTable.parent = tableStack.peek();
            currentSymbolTable = tableStack.pop();
        }
    }

    private void addSymbolDataToCurrentScope(String id, SymbolData symbolData) {
        currentSymbolTable.getScope().put(id, symbolData);
    }

    private void addMethodScopeToClassScope(MethodScope methodScope, ClassScope classScope, String id) {
        classScope.getMethodScopeMap().put(id, methodScope);
    }

    private void createMethodForClassScope(SymbolData returnType, List<KxiParameter> parameters, ScopeType scopeType, String id) {
        ClassScope classScope = (ClassScope) tableStack.peek();
        BlockScope blockScope = (BlockScope) currentSymbolTable;
        blockScope.setScopeType(scopeType);

        //make params for method scope
        List<SymbolData> params = new ArrayList<>();
        for (KxiParameter parameter : parameters) {
            SymbolData symbolData = new SymbolData(false, Modifier.PRIVATE, parameter.getType());
            params.add(symbolData);
        }

        MethodScope methodScope = new MethodScope(returnType, params, blockScope);
        addMethodScopeToClassScope(methodScope, classScope, id);
    }

    private void setBlockScopeType(ScopeType scopeType) {
        BlockScope blockScope = (BlockScope) currentSymbolTable;
        blockScope.setScopeType(scopeType);
    }

    @Override
    public void visit(KxiMain kxiMain) {
        //check if id is 'main'
        if (kxiMain.getId().getValue() != "main") {
            exceptionStack.push(new SymbolTableException(kxiMain.getLineInfo(), "main must be named 'main'"));
        }
        setBlockScopeType(ScopeType.Main);
        globalScope.setMainScope((BlockScope) currentSymbolTable);
        scopeNodeVisit();

    }

    @Override
    public void preVisit(KxiClass kxiClass) {
        ClassScope classScope = new ClassScope();
        classScope.setClassId(kxiClass.getId().getValue());
        kxiClass.setScope(classScope);
        scopeNodePreVisit(classScope);
    }

    @Override
    public void visit(KxiClass kxiClass) {
        scopeHandler.addClassScope(kxiClass.getId().getValue(), (ClassScope) kxiClass.getScope());
        scopeNodeVisit();
    }

    @Override
    public void preVisit(KxiBlock kxiBlock) {
        BlockScope blockScope = new BlockScope();
        kxiBlock.setScope(blockScope);
        scopeNodePreVisit(blockScope);
    }

    @Override
    public void visit(KxiMethod kxiMethod) {

        //data to add to symbol table and method scope
        SymbolData returnData = new SymbolData(kxiMethod.isStatic(), kxiMethod.getModifier(), kxiMethod.getReturnType());

        String id = kxiMethod.getId().getValue();

        createMethodForClassScope(returnData, kxiMethod.getParameters(), ScopeType.Method, id);

        scopeNodeVisit();

        addSymbolDataToCurrentScope(id, returnData);

    }

    @Override
    public void visit(KxiConstructor kxiConstructor) {
        SymbolData symbolData = new SymbolData(false, Modifier.PUBLIC, new KxiType(ScalarType.VOID, kxiConstructor.getId()));
        String id = kxiConstructor.getId().getValue();

        //check if constructor matches class name
        if (id != ((ClassScope) tableStack.peek()).getClassId()) {
            exceptionStack.push(new SymbolTableException(kxiConstructor.getLineInfo(), "Constructor ID does not match class ID"));
        }

        createMethodForClassScope(symbolData, kxiConstructor.getParameters(), ScopeType.Constructor, id);

        scopeNodeVisit();
    }

    @Override
    public void visit(KxiIfStatement kxiIfStatement) {
        setBlockScopeType(ScopeType.If);
        scopeNodeVisit();
    }

    @Override
    public void visit(KxiForStatement kxiForStatement) {
        setBlockScopeType(ScopeType.For);
        scopeNodeVisit();
    }

    @Override
    public void visit(KxiWhileStatement kxiWhileStatement) {
        setBlockScopeType(ScopeType.While);
        scopeNodeVisit();
    }

    @Override
    public void visit(KxiSwitchStatementChar kxiSwitchStatementChar) {
        setBlockScopeType(ScopeType.Switch);
        scopeNodeVisit();
    }

    @Override
    public void visit(KxiSwitchStatementInt kxiSwitchStatementInt) {
        setBlockScopeType(ScopeType.Switch);
        scopeNodeVisit();
    }

    @Override
    public void visit(KxiVariableDeclaration kxiVariableDeclaration) {
        SymbolData symbolData = new SymbolData(false, Modifier.LOCAL, kxiVariableDeclaration.getType());
        addSymbolDataToCurrentScope(kxiVariableDeclaration.getId().getValue(), symbolData);
    }

    @Override
    public void visit(KxiDataMember kxiDataMember) {
        //does not visit its child variable declaration, this way we can have them separate
        SymbolData symbolData = new SymbolData(kxiDataMember.isStatic(), kxiDataMember.getModifier(), kxiDataMember.getVariableDeclaration().getType());
        addSymbolDataToCurrentScope(kxiDataMember.getVariableDeclaration().getId().getValue(), symbolData);
    }

    @Override
    public void visit(KxiParameter kxiParameter) {
        SymbolData symbolData = new SymbolData(false, Modifier.LOCAL, kxiParameter.getType());
        addSymbolDataToCurrentScope(kxiParameter.getId().getValue(), symbolData);
    }

}
