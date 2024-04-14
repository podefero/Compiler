package compilers.ast.intermediate;

import lombok.Getter;

@Getter
public enum OpCodes {
    JMP(1),
    JMR(2),
    BNZ(3),
    ADD(13),
    ADI(14),
    TRP(21);
    private final int value;

    OpCodes(int value) {
        this.value = value;
    }
}
