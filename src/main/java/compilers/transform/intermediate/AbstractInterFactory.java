package compilers.transform.intermediate;

import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.transform.AbstractFactory;

import java.util.Stack;

public abstract class AbstractInterFactory extends AbstractFactory<AbstractKxiNode, AbstractInterNode> {

    @Override
    public AbstractInterNode build(AbstractKxiNode ctx, Stack<AbstractInterNode> stack) {
        return new InterFactoryDefault().build(ctx, stack);
    }
}
