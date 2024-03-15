package compilers.transform.kxi;


import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiCase;
import compilers.ast.kxi_nodes.expressions.*;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.token_expression.*;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniPlus;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.other.KxiInvalidNode;
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
                return new KxiExpressionArguments(popList(stack, getListSizeFromCtx(expressionContext.arguments().argumentList().expression()))
                        , pop(stack));
            else
                return new KxiExpressionArguments(popList(stack, 0), pop(stack));
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
                case GREATEREQUALS:
                    return new KxiGreaterEqualsThen(pop(stack), pop(stack));
                case AND:
                    return new KxiAnd(pop(stack), pop(stack));
                case OR:
                    return new KxiOr(pop(stack), pop(stack));
                case DOT:
                    return new KxiDotExpression(pop(stack), new IdentifierToken(getTokenText(expressionContext.IDENTIFIER())));
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
                case LPARENTH:
                    return new KxiParenthExpression(pop(stack));
                case INTLIT:
                    return new IntLitToken(getTokenText(expressionContext.INTLIT()));
                case CHARLIT:
                    return new CharLitToken(getTokenText(expressionContext.CHARLIT()));
                case STRINGLIT:
                    return new StringLitToken(getTokenText(expressionContext.STRINGLIT()));
                case TRUE:
                    return new BoolToken(getTokenText(expressionContext.TRUE()));
                case FALSE:
                    return new BoolToken(getTokenText(expressionContext.FALSE()));
                case NULL:
                    return new NullToken(getTokenText(expressionContext.NULL()));
                case THIS:
                    return new ThisToken(getTokenText(expressionContext.THIS()));
                case IDENTIFIER:
                    return new IdentifierToken(getTokenText(expressionContext.IDENTIFIER()));
                case NEW:
                    if (expressionContext.index() != null)
                        return new KxiNewExpressionIndex(pop(stack), pop(stack));
                    else {
                        if (expressionContext.arguments().argumentList() != null)
                            return new KxiNewExpressionArgument(popList(stack, getListSizeFromCtx(expressionContext.arguments().argumentList().expression()))
                                    , pop(stack));

                    }
                default:
                    break;
            }
        }
        return new KxiInvalidNode(ctx, stack, null);
    }
}
