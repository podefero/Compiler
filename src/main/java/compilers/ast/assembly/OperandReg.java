package compilers.ast.assembly;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OperandReg extends Operand{
    Registers registers;
}
