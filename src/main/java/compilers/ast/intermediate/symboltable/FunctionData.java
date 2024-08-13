package compilers.ast.intermediate.symboltable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FunctionData {
    String id;
    String label;
    int numParam;
    ActivationRecord activationRecord;


    public int getSize() {
        return activationRecord.getStackDataMap().size();
    }

}
