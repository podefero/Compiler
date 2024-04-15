package compilers.ast.assembly;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class AssemblyCode extends AbstractAssembly {
    private String label;
    private String opCodes;
    private Operand operandL;
    private Operand operandR;

    public AssemblyCode(String label, String opCodes, Operand operandL, Operand operandR) {
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
