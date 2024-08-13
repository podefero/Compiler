package compilers.ast.intermediate.symboltable;

import compilers.ast.intermediate.StackType;
import compilers.util.DataSizes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ActivationRecord {
    Map<String, StackData> stackDataMap;

    public void pushStackItem(String id, StackType stackType) {
        int offset = stackDataMap.size() * DataSizes.INT_SIZE;
        stackDataMap.put(id, new StackData(offset, stackType, id));
    }
}
