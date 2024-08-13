package compilers.transform.kxi;

import compilers.ast.kxi_nodes.AbstractKxiNode;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Stack;

public class KxiFactoryBase extends AbstractKxiFactory {
    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Stack<AbstractKxiNode> stack) {
        return super.build(ctx, stack);
    }
}
