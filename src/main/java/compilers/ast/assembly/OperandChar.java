package compilers.ast.assembly;

import lombok.Getter;

@Getter
public class OperandChar extends Operand {
    char character;

    public OperandChar(char character) {
        this.character = character;
    }
}
