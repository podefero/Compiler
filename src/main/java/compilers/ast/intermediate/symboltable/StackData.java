package compilers.ast.intermediate.symboltable;

import compilers.ast.intermediate.StackType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StackData {
    int offset;
    StackType stackType;
    String id;
}
