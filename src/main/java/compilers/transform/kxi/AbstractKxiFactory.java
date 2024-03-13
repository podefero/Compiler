package compilers.transform.kxi;

import compilers.antlr.KxiParser;
import compilers.ast.AbstractKxiNode;
import compilers.transform.AbstractFactory;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Queue;

public abstract class AbstractKxiFactory extends AbstractFactory<ParserRuleContext, AbstractKxiNode> {

    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Queue<AbstractKxiNode> queue) {
        if (ctx instanceof KxiParser.ExpressionContext)
            return new KxiFactoryExpression().build(ctx, queue);
        else if (ctx instanceof KxiParser.StatementContext)
            return new KxiFactoryStatement().build(ctx, queue);
        else
            return new KxiFactoryDefault().build(ctx, queue);
    }


}
