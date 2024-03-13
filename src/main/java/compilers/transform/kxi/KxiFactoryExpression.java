package compilers.transform.kxi;


import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.KxiExpression;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.util.KxiParseHelper;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Queue;

import static compilers.antlr.KxiParser.*;

public class KxiFactoryExpression extends AbstractKxiFactory {

    public KxiFactoryExpression(ParserRuleContext ctx, Queue<AbstractKxiNode> queue) {
        super(ctx, queue);
    }

    @Override
    public AbstractKxiNode build() {
        KxiParseHelper parseHelper = new KxiParseHelper();
        int type = parseHelper.getTokenType(parseHelper.findFirstTerminal(ctx.children));
        switch (type) {
            case EQUALS:
                return new KxiEquals((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case PLUSEQUALS:
                return new KxiPlusEquals((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case SUBEQUALS:
                return new KxiSubtractEquals((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case MULTEQUALS:
                return new KxiMultEquals((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case DIVEQUALS:
                return new KxiDivEquals((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case MULT:
                return new KxiMult((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case DIVIDE:
                return new KxiDiv((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case PLUS:
                return new KxiPlus((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case SUBTRACT:
                return new KxiSubtract((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case EQUALSEQUALS:
                return new KxiEqualsEquals((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case NOTEQUALS:
                return new KxiNotEquals((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case LESSTHEN:
                return new KxiLessThen((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case GREATEREQUALS:
                return new KxiGreaterEqualsThen((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case AND:
                return new KxiAnd((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            case OR:
                return new KxiOr((AbstractKxiExpression) queue.poll(), (AbstractKxiExpression) queue.poll());
            default:
                return new KxiExpression();
        }
    }
}
