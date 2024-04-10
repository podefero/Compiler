package compilers.ast.intermediate;

public class InterInstructionNode extends AbstractInterNode implements Assemble{
    private OpCodes opCodes;
    private int offset;


    @Override
    public String getAssembly() {
        return null;
    }
}
