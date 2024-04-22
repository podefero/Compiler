package compilers.ast.assembly;

import lombok.Getter;

@Getter
public enum OpCodes {
    JMP("JMP"),
    JMR("JMR"),
    BNZ("BNZ"),
    ADD("ADD"),
    SUB("SUB"),
    MUL("MUL"),
    DIV("DIV"),
    ADI("ADDI"),
    CMP("CMP"),
    BRZ("BRZ"),
    BGT("BGT"),
    BLT("BLT"),
    CMPI("CMPI"),
    AND("AND"),
    OR("OR"),
    NOT("NOT"),
    LDR("LDR"),
    LDRI("LDR"),
    LDA("LDA"),
    MOV("MOV"),
    MOVI("MOVI"),
    STR("STR"),
    STRI("STR"),
    PUSH("PUSH"),
    POP("POP"),
    TRP("TRP");
    private final String value;

    OpCodes(String value) {
        this.value = value;
    }
}
