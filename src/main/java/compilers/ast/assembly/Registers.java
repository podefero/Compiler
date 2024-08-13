package compilers.ast.assembly;

import lombok.Getter;

@Getter
public enum Registers {
    R0("R0"),
    R1("R1"),
    R2("R2"),
    R3("R3"),
    R4("R4"),
    R5("R5"),
    R6("R6"),
    R7("R7"),
    R8("R8"),
    R9("R9"),
    R10("R10"),
    R11("R11"),
    R12("R12"),
    R13("R13"),
    R14("R14"),
    R15("R15"),
    PC("PC"),
    SL("SL"),
    SB("SB"),
    SP("SP"),
    FP("FP"),
    ;
    private final String value;

    Registers(String value) {
        this.value = value;
    }
}


