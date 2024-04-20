package compilers.ast.assembly;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Data;
import lombok.Getter;

@Data
public class AssemblyCodeReturnAddress extends AbstractAssembly {
    private String label;
    private String opCodes;
    private Operand operandL;
    private OperandInteger operandR;

    public AssemblyCodeReturnAddress(String label, String opCodes, Operand operandL, OperandInteger operandR) {
        super(operandR, operandL);
        this.label = label;
        this.opCodes = opCodes;
        this.operandL = operandL;
        this.operandR = operandR;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
