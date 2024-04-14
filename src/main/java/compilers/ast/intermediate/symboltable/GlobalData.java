package compilers.ast.intermediate.symboltable;

import compilers.ast.intermediate.DirType;
import compilers.ast.intermediate.Label;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GlobalData {
    String id;
    DirType dirType;
    String label;
}
