package compilers.transform.kxi;

import compilers.ast.AbstractKxiNode;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Queue;

public class KxiFactoryBase extends AbstractKxiFactory {
    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Queue<AbstractKxiNode> queue) {
        return super.build(ctx, queue);
    }
}
