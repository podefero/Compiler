package compilers.ast.intermediate.symboltable;

import compilers.ast.intermediate.StackType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StackData {
    int offset;
    StackType stackType;
    String id;
}
