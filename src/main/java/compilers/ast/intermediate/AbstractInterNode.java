package compilers.ast.intermediate;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.InterOperand.InterOperand;

public abstract class AbstractInterNode extends GenericNode {
    protected InterOperand interOperand;
    public AbstractInterNode(GenericNode... genericNodes) {
        super(genericNodes);
    }

    public String convertIdToLabel(String id) {
        String label = id.replace('$', '_');
        return label;
    }
}
