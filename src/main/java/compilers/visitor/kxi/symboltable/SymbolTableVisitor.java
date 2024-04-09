package compilers.visitor.kxi.symboltable;

import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.class_members.KxiConstructor;
import compilers.ast.kxi_nodes.class_members.KxiDataMember;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.ast.kxi_nodes.statements.KxiSwitchStatement;
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
    private List<String> allIds;
    private int nameCounter;

    public SymbolTableVisitor() {
        super();
        scopeHandler = new ScopeHandler();
        tableStack = new Stack<>();
        globalScope = new GlobalScope();
        allIds = new ArrayList<>();
        nameCounter = 0;
    }


    private void setScopeUniqueName() {
        if (currentSymbolTable instanceof ClassScope) {
            currentSymbolTable.setUniqueName(((ClassScope) currentSymbolTable).getClassId());
        } else if (currentSymbolTable instanceof BlockScope) {
            if (!tableStack.empty()) {
                nameCounter++;
                currentSymbolTable.setUniqueName(tableStack.peek().getUniqueName() + nameCounter);
            } else {
                currentSymbolTable.setUniqueName("Main");
            }

        }
    }

    private void scopeNodePreVisit(SymbolTable symbolTable) {
        if (currentSymbolTable != null) {
            tableStack.push(currentSymbolTable);
        }
        currentSymbolTable = symbolTable;
        setScopeUniqueName();
    }

    private void scopeNodeVisit() {
        if (tableStack.empty()) {
            //if(currentSymbolTable instanceof BlockScope) System.out.println(((BlockScope) currentSymbolTable).getScopeType());
            currentSymbolTable.parent = globalScope;
            currentSymbolTable = globalScope;
        } else {
            SymbolTable parent = tableStack.peek();
            //if(currentSymbolTable instanceof BlockScope) System.out.println(((BlockScope) currentSymbolTable).getScopeType());
            currentSymbolTable.parent = parent;
            currentSymbolTable = tableStack.pop();
        }
    }

    private void checkForDuplicateId(SymbolTable symbolTable, String key, String lineInfo) {
        //make sure class name does not exist

        if (symbolTable.getScope().containsKey(key)) {
            SymbolData symbolData = symbolTable.getScope().get(key);
            KxiAbstractType type = symbolData.getType();
            exceptionStack.push(new SymbolTableException(lineInfo, "Duplicate ID name " + key));
        }
        ClassScope classScope = scopeHandler.getClassScope(key);
        if (classScope != null)
            exceptionStack.push(new SymbolTableException(lineInfo, "ID can't be same as class name " + key));
    }


    private void addSymbolDataToCurrentScope(String id, SymbolData symbolData) {
        if (!allIds.contains(id)) allIds.add(id);

        ScalarType scalarType = symbolData.getType().getScalarType();
        boolean isStatic = symbolData.isStatic();

        if (isStatic || scalarType == ScalarType.STRING) {
            globalScope.getScope().put(currentSymbolTable.getUniqueName() + id, symbolData);
        } else {
            checkForDuplicateId(currentSymbolTable, id, symbolData.getType().getLineInfo());
            currentSymbolTable.getScope().put(id, symbolData);
        }
    }

    private void addMethodScopeToClassScope(MethodScope methodScope, ClassScope classScope, String id, String lineInfo) {
        if (classScope.getMethodScopeMap().containsKey(id))
            exceptionStack.push(new SymbolTableException(lineInfo, "Duplicate Method/Constructor name " + id));

        classScope.getMethodScopeMap().put(id, methodScope);
    }

    private void createMethodForClassScope(SymbolData returnType, List<KxiParameter> parameters, String id, String lineInfo) {
        ClassScope classScope = (ClassScope) tableStack.peek();
        BlockScope blockScope = (BlockScope) currentSymbolTable;

        //make params for method scope
        List<SymbolData> params = new ArrayList<>();
        for (KxiParameter parameter : parameters) {
            SymbolData symbolData = new SymbolData(false, Modifier.PRIVATE, parameter.getType());
            params.add(symbolData);
        }

        MethodScope methodScope = new MethodScope(returnType, params, blockScope);
        blockScope.setMethodId(id);
        addMethodScopeToClassScope(methodScope, classScope, id, lineInfo);
    }


    private void setBlockScopeType(ScopeType scopeType) {
        BlockScope blockScope = (BlockScope) currentSymbolTable;
        blockScope.setScopeType(scopeType);
    }

    @Override
    public void visit(KxiMain kxiMain) {
        //check if id is 'main'
        if (!kxiMain.getId().getValue().equals("main")) {
            exceptionStack.push(new SymbolTableException(kxiMain.getLineInfo(), "main must be named 'main'"));
        }
        setBlockScopeType(ScopeType.Main);
        MethodScope methodScope =
                new MethodScope(new SymbolData(false, null, new KxiType(ScalarType.VOID, kxiMain.getId()))
                        , new ArrayList<>(), (BlockScope) currentSymbolTable);

        globalScope.setMainScope(methodScope);
        scopeNodeVisit();
        scopeHandler.setGlobalScope(globalScope);
    }

    @Override
    public void preVisit(KxiClass kxiClass) {
        ClassScope classScope = new ClassScope();
        classScope.setClassId(kxiClass.getId().getValue());
        kxiClass.setScope(classScope);
        if (scopeHandler.getClassScopeMap().containsKey(classScope.getClassId()))
            exceptionStack.push(new SymbolTableException(kxiClass.getLineInfo(), "Duplicate class " + classScope.getClassId()));

        if (allIds.contains(classScope.getClassId()))
            exceptionStack.push(new SymbolTableException(kxiClass.getLineInfo(), "ID exists " + classScope.getClassId()));

        scopeHandler.addClassScope(classScope.getClassId(), (ClassScope) kxiClass.getScope());
        scopeNodePreVisit(classScope);
    }

    @Override
    public void visit(KxiClass kxiClass) {
        String id = kxiClass.getId().getValue();
        nameCounter = 0;
        scopeNodeVisit();
    }

    @Override
    public void preVisit(KxiBlock kxiBlock) {
        BlockScope blockScope = new BlockScope();
        kxiBlock.setScope(blockScope);
        scopeNodePreVisit(blockScope);
    }

    @Override
    public void preVisit(KxiCaseBlock kxiBlock) {
        BlockScope blockScope = new BlockScope();
        kxiBlock.setScope(blockScope);

        scopeNodePreVisit(blockScope);
    }


    @Override
    public void visit(KxiMethod kxiMethod) {

        //data to add to symbol table and method scope
        SymbolData returnData = new SymbolData(kxiMethod.isStatic(), kxiMethod.getModifier(), kxiMethod.getReturnType());

        String id = kxiMethod.getId().getValue();

        createMethodForClassScope(returnData, kxiMethod.getParameters(), id, kxiMethod.getLineInfo());

        setBlockScopeType(ScopeType.Method);

        scopeNodeVisit();

        addSymbolDataToCurrentScope(id, returnData);

    }

    @Override
    public void visit(KxiConstructor kxiConstructor) {
        SymbolData symbolData = new SymbolData(false, Modifier.PUBLIC, new KxiType(ScalarType.VOID, kxiConstructor.getId()));
        String id = kxiConstructor.getId().getValue();

        //check if constructor matches class name
        if (!id.equals(((ClassScope) tableStack.peek()).getClassId())) {
            exceptionStack.push(new SymbolTableException(kxiConstructor.getLineInfo(), "Constructor ID does not match class ID"));
        }

        createMethodForClassScope(symbolData, kxiConstructor.getParameters(), id, kxiConstructor.getLineInfo());

        setBlockScopeType(ScopeType.Constructor);

        scopeNodeVisit();
    }

    @Override
    public void visit(KxiIfStatement kxiIfStatement) {
        if (kxiIfStatement.getElseStatement() != null) {
            setBlockScopeType(ScopeType.If);
            scopeNodeVisit();
        }
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
    public void visit(KxiSwitchStatement kxiSwitchStatementChar) {
        setBlockScopeType(ScopeType.Switch);
        scopeNodeVisit();
    }



    @Override
    public void visit(KxiVariableDeclaration kxiVariableDeclaration) {
        SymbolData symbolData = new SymbolData(false, null, kxiVariableDeclaration.getType());
        addSymbolDataToCurrentScope(kxiVariableDeclaration.getId().getValue(), symbolData);
    }

    @Override
    public void visit(KxiDataMember kxiDataMember) {
        SymbolData symbolData = new SymbolData(kxiDataMember.isStatic(), kxiDataMember.getModifier(), kxiDataMember.getVariableDeclaration().getType());
        String id = kxiDataMember.getVariableDeclaration().getId().getValue();
        //updates key
        currentSymbolTable.getScope().put(id, symbolData);
        //addSymbolDataToCurrentScope(kxiDataMember.getVariableDeclaration().getId().getValue(), symbolData);
    }

    @Override
    public void visit(KxiParameter kxiParameter) {
        SymbolData symbolData = new SymbolData(false, null, kxiParameter.getType());
        addSymbolDataToCurrentScope(kxiParameter.getId().getValue(), symbolData);
    }

}
