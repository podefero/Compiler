package compilers.ast.intermediate.statements;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.AbstractInterNode;

public abstract class InterStatement extends AbstractInterNode {
    public InterStatement(GenericNode... genericNodes) {
        super(genericNodes);
    }

}
