package compilers.visitor.intermediate;

import compilers.ast.intermediate.InterFunctionNode;
import compilers.ast.intermediate.StackType;
import compilers.ast.intermediate.statements.InterFunctionalCall;
import compilers.ast.intermediate.statements.InterGlobalVariable;
import compilers.ast.intermediate.statements.InterVariable;
import compilers.ast.intermediate.symboltable.*;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
public class InterSymbolTableVisitor extends KxiVisitorBase {
    private InterSymbolTable interSymbolTable;
    private FunctionData currentFunctionData;

    private String convertIdToLabel(String id) {
        String label = id.replace('$', '_');
        return label;
    }

    @Override
    public void preVisit(InterFunctionNode node) {
        String id = node.getInterId().getId();
        String label = convertIdToLabel(id);
        FunctionData functionData = new FunctionData(id, label, 0, new ActivationRecord(new HashMap<>()));
        ActivationRecord activationRecord = functionData.getActivationRecord();
        //have two items on stack, return address and pfp
        activationRecord.pushStackItem(node.getReturnId(), StackType.PARAM);
        activationRecord.pushStackItem(node.getPfpId(), StackType.PARAM);
        currentFunctionData = functionData;
        interSymbolTable.getFunctionDataMap().put(id, functionData);
    }


    @Override
    public void visit(InterVariable node) {
        String id = node.getInterId().getId();
        currentFunctionData.getActivationRecord().pushStackItem(id, StackType.LOCAL);
    }

    @Override
    public void visit(InterGlobalVariable node) {
        String id = node.getInterId().getId();
        GlobalData globalData = new GlobalData(id, node.getDirective(), convertIdToLabel(id));
        interSymbolTable.getGlobalDataMap().put(id, globalData);
    }
}
