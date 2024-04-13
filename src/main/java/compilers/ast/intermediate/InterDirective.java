package compilers.ast.intermediate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InterDirective extends AbstractInterNode implements Assemble{
    private String label;
    private OpCodes opCodes;
    private String operandL;
    private String operandR;


    @Override
    public String getAssembly() {
        return label + " " + opCodes + " " + operandL + " " + operandR;
    }
}
