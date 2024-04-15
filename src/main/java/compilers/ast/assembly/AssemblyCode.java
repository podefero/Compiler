package compilers.ast.assembly;

public class AssemblyCode extends AbstractAssembly {
    private String label;
    private String opCodes;
    private Operand operandL;
    private Operand operandR;

    public AssemblyCode(String label, String opCodes, Operand operandL, Operand operandR) {
        super(operandR, operandL);
    }


}
