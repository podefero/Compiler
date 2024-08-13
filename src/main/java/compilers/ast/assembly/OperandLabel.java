package compilers.ast.assembly;

public class OperandLabel extends Operand {
    String label;

    public OperandLabel(String label) {
        this.label = label;
        this.value = label;
    }
}
