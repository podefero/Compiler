package compilers.ast.intermediate.symboltable;

import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.DirType;
import compilers.ast.intermediate.InterLit;
import compilers.ast.intermediate.Label;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GlobalData {
    String id;
    Directive dirType;
    String label;
    InterLit interLit;
}
