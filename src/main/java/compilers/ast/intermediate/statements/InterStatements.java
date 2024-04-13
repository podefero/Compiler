package compilers.ast.intermediate.statements;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.AbstractInterNode;

public abstract class InterStatements extends AbstractInterNode {
    public InterStatements(GenericNode... genericNodes) {
        super(genericNodes);
    }

}
