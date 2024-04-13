package compilers.ast.intermediate.symboltable;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class ActivationRecord {
    Map<String, StackData> stackDataMap;
}
