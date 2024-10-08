package compilers.transform.kxi;


import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiArguments;
import compilers.ast.kxi_nodes.KxiIndex;
import compilers.ast.kxi_nodes.expressions.*;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniPlus;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.helper.KxiInvalidNode;
import compilers.ast.kxi_nodes.token_literals.*;
import compilers.util.KxiParseHelper;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Stack;

import static compilers.antlr.KxiParser.*;

public class KxiFactoryExpression extends AbstractKxiFactory {


    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Stack<AbstractKxiNode> stack) {
        ExpressionContext expressionContext = (ExpressionContext) ctx;


        //Expression with only non-terminals
        if (expressionContext.expression(0) != null && expressionContext.index() != null)
            return new KxiExpressionIndex(new KxiIndex(pop(stack)), pop(stack));
        else if (expressionContext.expression(0) != null && expressionContext.arguments() != null) {
            if (expressionContext.arguments().argumentList() != null) {
                KxiArguments kxiArguments = new KxiArguments(popList(stack, getListSizeFromCtx(expressionContext.arguments().argumentList().expression())));
                AbstractKxiExpression methodExp = pop(stack);
                kxiArguments.setLineInfo(methodExp.getLineInfo());
                return new KxiMethodExpression(kxiArguments,methodExp);
            }
            else
                return new KxiMethodExpression(new KxiArguments(new GenericListNode(new ArrayList<>())), pop(stack));
        }

        KxiParseHelper parseHelper = new KxiParseHelper();
        TerminalNode terminalNode = parseHelper.findFirstTerminal(ctx.children);
        int type = parseHelper.getTokenType(terminalNode);

        //handle the pattern expression terminal expression
        if (ctx.children.size() == 3) {
            switch (type) {
                case EQ:
                    return new KxiEquals(pop(stack), pop(stack));
                case PLUSEQ:
                    return new KxiPlusEquals(pop(stack), pop(stack));
                case MINUSEQ:
                    return new KxiSubtractEquals(pop(stack), pop(stack));
                case TIMESEQ:
                    return new KxiMultEquals(pop(stack), pop(stack));
                case DIVIDEEQ:
                    return new KxiDivEquals(pop(stack), pop(stack));
                case TIMES:
                    return new KxiMult(pop(stack), pop(stack));
                case DIVIDE:
                    return new KxiDiv(pop(stack), pop(stack));
                case PLUS:
                    return new KxiPlus(pop(stack), pop(stack));
                case MINUS:
                    return new KxiSubtract(pop(stack), pop(stack));
                case EQEQ:
                    return new KxiEqualsEquals(pop(stack), pop(stack));
                case NOTEQ:
                    return new KxiNotEquals(pop(stack), pop(stack));
                case LT:
                    return new KxiLessThen(pop(stack), pop(stack));
                case GT:
                    return new KxiGreaterThen(pop(stack), pop(stack));
                case LEQ:
                    return new KxiLessEqualsThen(pop(stack), pop(stack));
                case GEQ:
                    return new KxiGreaterEqualsThen(pop(stack), pop(stack));
                case AND:
                    return new KxiAnd(pop(stack), pop(stack));
                case OR:
                    return new KxiOr(pop(stack), pop(stack));
                case DOT:
                    return new KxiDotExpression(new IdentifierToken(getTokenText(expressionContext.ID())), pop(stack));
                case LPAREN:
                    return new KxiParenthExpression(pop(stack));
                case NEW:
                    if (expressionContext.index() != null)
                        return new KxiNewExpressionIndex(new KxiIndex(pop(stack)), pop(stack));
                    else {
                        if (expressionContext.arguments().argumentList() != null)
                            return new KxiNewExpressionArgument(new KxiArguments(popList(stack, getListSizeFromCtx(expressionContext.arguments().argumentList().expression())))
                                    , new IdentifierToken(getTokenText(expressionContext.ID())));
                        else
                            return new KxiNewExpressionArgument(new KxiArguments(new GenericListNode(new ArrayList<>())), new IdentifierToken(getTokenText(expressionContext.ID())));


                    }
                default:
                    break;
            }
        }
        //handle other expression with terminal
        else {
            switch (type) {
                case NOT:
                    return new KxiNot(pop(stack));
                case PLUS:
                    return new KxiUniPlus(pop(stack));
                case MINUS:
                    return new KxiUniSubtract(pop(stack));
                case INTLIT:
                    return new ExpressionIntLit(new IntLitToken(getTokenText(expressionContext.INTLIT())));
                case CHARLIT:
                    return new ExpressionCharLit(new CharLitToken(getTokenText(expressionContext.CHARLIT())));
                case STRINGLIT:
                    return new ExpressionStringLit(new StringLitToken(getTokenText(expressionContext.STRINGLIT())));
                case TRUE:
                    return new ExpressionBoolLit(new BoolToken(getTokenText(expressionContext.TRUE())));
                case FALSE:
                    return new ExpressionBoolLit(new BoolToken(getTokenText(expressionContext.FALSE())));
                case NULL:
                    return new ExpressionNullLit(new NullToken(getTokenText(expressionContext.NULL())));
                case THIS:
                    return new ExpressionThisLit(new ThisToken(getTokenText(expressionContext.THIS())));
                case ID:
                    return new ExpressionIdLit(new IdentifierToken(getTokenText(expressionContext.ID())));
                default:
                    break;
            }
        }
        return null;
    }
}
