package compilers.ast.intermediate;

import compilers.ast.GenericNode;
import compilers.ast.assembly.AssemblyBlock;
import compilers.ast.intermediate.InterOperand.InterOperand;
import lombok.Data;

@Data
public abstract class AbstractInterNode extends GenericNode {
    protected InterOperand interOperand;
    protected AssemblyBlock assemblyBlock;

    public AbstractInterNode(GenericNode... genericNodes) {
        super(genericNodes);
        assemblyBlock = new AssemblyBlock();
    }

    public String convertIdToLabel(String id) {
        String label = id.replace('$', '_');
        return label;
    }
}
