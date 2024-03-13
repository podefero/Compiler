package compilers.transform.kxi;

import compilers.ast.AbstractKxiNode;
import compilers.transform.AbstractFactory;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Queue;

public abstract class AbstractKxiFactory extends AbstractFactory<ParserRuleContext, AbstractKxiNode> {
    public AbstractKxiFactory(ParserRuleContext ctx, Queue<AbstractKxiNode> queue) {
        super(ctx, queue);
    }
}
