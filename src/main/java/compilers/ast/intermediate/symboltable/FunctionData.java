package compilers.ast.intermediate.symboltable;

import compilers.util.DataSizes;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FunctionData {
    String id;
    String label;
    ActivationRecord activationRecord;


    public int getSize() {
        return activationRecord.getStackDataMap().size() * DataSizes.INT_SIZE;
    }

}
