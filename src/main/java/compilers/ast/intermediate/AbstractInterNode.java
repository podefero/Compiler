package compilers.ast.intermediate;

import compilers.ast.GenericNode;

public abstract class AbstractInterNode extends GenericNode {
    public AbstractInterNode(GenericNode... genericNodes) {
        super(genericNodes);
    }

    public String gatherAssembly() {
        return null;
    }

}
