package compilers.ast.assembly;

import lombok.Getter;

@Getter
public class OperandInteger extends Operand {
    String numInteger;

    public OperandInteger(int numInteger) {
        this.numInteger = "#" + numInteger;
        this.value = this.numInteger;
    }
}
