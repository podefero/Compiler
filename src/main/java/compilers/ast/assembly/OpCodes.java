package compilers.ast.assembly;

import lombok.Getter;

@Getter
public enum OpCodes {
    JMP("JMP"),
    JMR("JMR"),
    BNZ("BNZ"),
    ADD("ADD"),
    ADI("ADDI"),
    LDR("LDR"),
    LDRI("LDR"),
    MOV("MOV"),
    MOVI("MOVI"),
    STR("STR"),
    STRI("STR"),
    PUSH("PUSH"),
    TRP("TRP");
    private final String value;

    OpCodes(String value) {
        this.value = value;
    }
}
