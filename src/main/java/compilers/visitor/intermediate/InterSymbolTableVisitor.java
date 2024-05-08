package compilers.visitor.intermediate;

import compilers.ast.intermediate.InterFunctionNode;
import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.StackType;
import compilers.ast.intermediate.statements.InterGlobalVariable;
import compilers.ast.intermediate.expression.InterVariable;
import compilers.ast.intermediate.symboltable.*;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.expressions.literals.ExpressionIdLit;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Getter
public class InterSymbolTableVisitor extends KxiVisitorBase {
    private InterSymbolTable interSymbolTable;
    private FunctionData currentFunctionData;
    private List<ExpressionIdLit> tempVars;

    private String convertIdToLabel(String id) {
        String label = id.replace('$', '_');
        return label;
    }

    @Override
    public void preVisit(KxiMain node) {
        String id = node.getId();
        String label = convertIdToLabel(id);
        FunctionData functionData = new FunctionData(id, label, 0, new ActivationRecord(new HashMap<>()));
        ActivationRecord activationRecord = functionData.getActivationRecord();
        //have two items on stack, return address and pfp
        activationRecord.pushStackItem(id + "return", StackType.PARAM);
        activationRecord.pushStackItem(id + "pfp", StackType.PARAM);
        currentFunctionData = functionData;
        interSymbolTable.getFunctionDataMap().put(id, functionData);

        for (ExpressionIdLit expressionIdLit : tempVars) {
            currentFunctionData.getActivationRecord().pushStackItem(expressionIdLit.getId(), StackType.LOCAL);
            if (expressionIdLit.getScalarType() == ScalarType.STRING) {
                if(interSymbolTable.getPtrVarMap().containsKey(expressionIdLit.getTempId())) {
                    String tempId = interSymbolTable.getPtrVarMap().get(expressionIdLit.getTempId());
                    expressionIdLit.setTempId(tempId);
                }
                interSymbolTable.getPtrVarMap().put(expressionIdLit.getId(), expressionIdLit.getTempId());
            }
        }

    }


    @Override
    public void visit(InterGlobalVariable node) {
        String id = node.getInterId().getId();
        GlobalData globalData = new GlobalData(id, node.getDirective(), convertIdToLabel(id), node.getInterLit());
        interSymbolTable.getGlobalDataMap().put(id, globalData);
    }
}
