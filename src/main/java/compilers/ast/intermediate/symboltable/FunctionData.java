package compilers.ast.intermediate.symboltable;

import compilers.ast.intermediate.Label;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FunctionData {
    String id;
    Label label;
    ActivationRecord activationRecord;
    int size;
}
