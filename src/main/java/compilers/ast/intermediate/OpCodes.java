package compilers.ast.intermediate;

public enum OpCodes {
    JMP(1),
    JMR(2),
    BNZ(3);
    private final int value;

    OpCodes(int value) {
        this.value = value;
    }
}
