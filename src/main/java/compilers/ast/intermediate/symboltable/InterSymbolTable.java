package compilers.ast.intermediate.symboltable;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class InterSymbolTable {
    Map<String, FunctionData> functionDataMap;
    Map<String, GlobalData> globalDataMap;
}
