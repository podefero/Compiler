package compilers.transform.kxi;

import compilers.antlr.KxiParser;
import compilers.ast.AbstractKxiNode;
import compilers.transform.AbstractFactory;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public abstract class AbstractKxiFactory extends AbstractFactory<ParserRuleContext, AbstractKxiNode> {

    public String getTokenText(TerminalNode terminalNode) {
        return terminalNode.getSymbol().getText();
    }

    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Stack<AbstractKxiNode> stack) {
        if (ctx instanceof KxiParser.ExpressionContext)
            return new KxiFactoryExpression().build(ctx, stack);
        else if (ctx instanceof KxiParser.StatementContext)
            return new KxiFactoryStatement().build(ctx, stack);
        else
            return new KxiFactoryDefault().build(ctx, stack);
    }


}
