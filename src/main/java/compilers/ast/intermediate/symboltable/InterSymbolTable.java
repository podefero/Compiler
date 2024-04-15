package compilers.ast.intermediate.symboltable;

import compilers.ast.intermediate.InterId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class InterSymbolTable {
    Map<String, FunctionData> functionDataMap;
    Map<String, GlobalData> globalDataMap;

    public String getFunctionLabel(InterId id) {
        return functionDataMap.get(id.getId()).getLabel();
    }

    public int getOffset(InterId id, FunctionData functionData) {
        StackData stackData = functionData.getActivationRecord().getStackDataMap().get(id.getId());
        return - stackData.offset; //needs to negative
    }
}
