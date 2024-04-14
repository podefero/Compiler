package compilers.ast.intermediate.symboltable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ActivationRecord {
    Map<String, StackData> stackDataMap;
}
