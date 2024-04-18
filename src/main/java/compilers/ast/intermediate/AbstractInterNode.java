package compilers.ast.intermediate;

import compilers.ast.GenericNode;

public abstract class AbstractInterNode extends GenericNode {
    public AbstractInterNode(GenericNode... genericNodes) {
        super(genericNodes);
    }

    public String convertIdToLabel(String id) {
        String label = id.replace('$', '_');
        return label;
    }
}
