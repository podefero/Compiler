package compilers.ast.assembly;

import lombok.Getter;

@Getter
public class OperandChar extends Operand {
    char character;

    public OperandChar(char character) {
        this.character = character;
        switch(character) {
            case '\n':
                this.value = "'\\n'";
                break;
            case '\r':
                this.value = "'\\r'";
                break;
            case '\t':
                this.value = "'\\t'";
                break;
            default:
                this.value = "'" + character + "'";
                break;
        }
    }
}
