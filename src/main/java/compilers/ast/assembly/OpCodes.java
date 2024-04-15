package compilers.ast.assembly;

import lombok.Getter;

@Getter
public enum OpCodes {
    JMP("JMP"),
    JMR("JMP"),
    BNZ("BNZ"),
    ADD("ADD"),
    ADI("ADDI"),
    LDR("LDR"),
    LDRI("LDR"),
    MOV("MOV"),
    MOVI("MOVI"),
    STR("STR"),
    STRI("STR"),
    TRP("TRP");
    private final String value;

    OpCodes(String value) {
        this.value = value;
    }
}
