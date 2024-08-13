package compilers.ast.assembly;

public class OperandReg extends Operand{
    Registers registers;

    public OperandReg(Registers registers) {
        this.registers = registers;
        this.value = registers.getValue();
    }
}
