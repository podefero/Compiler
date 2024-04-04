package compilers.transform.kxi;


import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiArguments;
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

import java.util.Stack;

import static compilers.antlr.KxiParser.*;

public class KxiFactoryExpression extends AbstractKxiFactory {


    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Stack<AbstractKxiNode> stack) {
        ExpressionContext expressionContext = (ExpressionContext) ctx;


        //Expression with only non-terminals
        if (expressionContext.expression(0) != null && expressionContext.index() != null)
            return new KxiExpressionIndex(pop(stack), pop(stack));
        else if (expressionContext.expression(0) != null && expressionContext.arguments() != null) {
            if (expressionContext.arguments().argumentList() != null)
                return new KxiMethodExpression(new KxiArguments(popList(stack, getListSizeFromCtx(expressionContext.arguments().argumentList().expression())))
                        , pop(stack));
            else
                return new KxiMethodExpression(null, pop(stack));
        }

        KxiParseHelper parseHelper = new KxiParseHelper();
        TerminalNode terminalNode = parseHelper.findFirstTerminal(ctx.children);
        int type = parseHelper.getTokenType(terminalNode);

        //handle the pattern expression terminal expression
        if (ctx.children.size() == 3) {
            switch (type) {
                case EQUALS:
                    return new KxiEquals(pop(stack), pop(stack));
                case PLUSEQUALS:
                    return new KxiPlusEquals(pop(stack), pop(stack));
                case SUBEQUALS:
                    return new KxiSubtractEquals(pop(stack), pop(stack));
                case MULTEQUALS:
                    return new KxiMultEquals(pop(stack), pop(stack));
                case DIVEQUALS:
                    return new KxiDivEquals(pop(stack), pop(stack));
                case MULT:
                    return new KxiMult(pop(stack), pop(stack));
                case DIVIDE:
                    return new KxiDiv(pop(stack), pop(stack));
                case PLUS:
                    return new KxiPlus(pop(stack), pop(stack));
                case SUBTRACT:
                    return new KxiSubtract(pop(stack), pop(stack));
                case EQUALSEQUALS:
                    return new KxiEqualsEquals(pop(stack), pop(stack));
                case NOTEQUALS:
                    return new KxiNotEquals(pop(stack), pop(stack));
                case LESSTHEN:
                    return new KxiLessThen(pop(stack), pop(stack));
                case GREATERTHEN:
                    return new KxiGreaterThen(pop(stack), pop(stack));
                case LESSEQUALS:
                    return new KxiLessEqualsThen(pop(stack), pop(stack));
                case GREATEREQUALS:
                    return new KxiGreaterEqualsThen(pop(stack), pop(stack));
                case AND:
                    return new KxiAnd(pop(stack), pop(stack));
                case OR:
                    return new KxiOr(pop(stack), pop(stack));
                case DOT:
                    return new KxiDotExpression(new IdentifierToken(getTokenText(expressionContext.IDENTIFIER())), pop(stack));
                case LPARENTH:
                    return new KxiParenthExpression(pop(stack));
                case NEW:
                    if (expressionContext.index() != null)
                        return new KxiNewExpressionIndex(pop(stack), pop(stack));
                    else {
                        if (expressionContext.arguments().argumentList() != null)
                            return new KxiNewExpressionArgument(new KxiArguments(popList(stack, getListSizeFromCtx(expressionContext.arguments().argumentList().expression())))
                                    , new IdentifierToken(getTokenText(expressionContext.IDENTIFIER())));
                        else
                            return new KxiNewExpressionArgument(null, new IdentifierToken(getTokenText(expressionContext.IDENTIFIER())));


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
                case SUBTRACT:
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
                case IDENTIFIER:
                    return new ExpressionIdLit(new IdentifierToken(getTokenText(expressionContext.IDENTIFIER())));
                default:
                    break;
            }
        }
        return new KxiInvalidNode(ctx, stack, null);
    }
}
