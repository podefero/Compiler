package compilers.ast.kxi_nodes;

import compilers.ast.GenericNode;
import compilers.ast.assembly.AssemblyBlock;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public abstract class AbstractKxiNode extends GenericNode {
    protected AssemblyBlock assemblyBlock;
    protected String lineInfo;
    public AbstractKxiNode(GenericNode ... genericNodes) {
        super(genericNodes);
        assemblyBlock = new AssemblyBlock();
    }

    public void acceptAbstractKxi(KxiVisitorBase kxiVisitorBase) {

    }
}
