package compilers.ast.kxi_nodes.scope;

import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.visitor.kxi.symboltable.SymbolTable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractKxiScope extends AbstractKxiNode {
    protected SymbolTable scope;
    public AbstractKxiScope(GenericNode... genericNodes) {
        super(genericNodes);
    }

}
