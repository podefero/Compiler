package compilers.visitor.intermediate;

import compilers.ast.intermediate.InterFunctionNode;
import compilers.ast.intermediate.StackType;
import compilers.ast.intermediate.statements.InterVariable;
import compilers.ast.intermediate.symboltable.ActivationRecord;
import compilers.ast.intermediate.symboltable.FunctionData;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.intermediate.symboltable.StackData;
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
        FunctionData functionData = new FunctionData(id, label, new ActivationRecord(new HashMap<>()));
        currentFunctionData = functionData;
        interSymbolTable.getFunctionDataMap().put(id, functionData);
    }

    @Override
    public void visit(InterVariable node) {
        String id = node.getInterId().getId();
        StackData stackData = new StackData(currentFunctionData.getSize(), StackType.LOCAL, id);
        currentFunctionData.getActivationRecord().getStackDataMap().put(id, stackData);
    }
}
