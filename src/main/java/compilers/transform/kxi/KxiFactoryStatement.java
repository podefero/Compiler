package compilers.transform.kxi;

import compilers.antlr.KxiParser;
import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.KxiPostForExpression;
import compilers.ast.kxi_nodes.expressions.KxiPreForExpression;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiElseStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static compilers.antlr.KxiParser.*;

public class KxiFactoryStatement extends AbstractKxiFactory {
    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Stack<AbstractKxiNode> stack) {
        StatementContext statementContext = (StatementContext) ctx;

        if (statementContext.variableDeclaration() != null) {
            return new KxiVariableDeclarationStatement(pop(stack));

        } else if (statementContext.SWITCH() != null) {
            return new KxiSwitchStatement(pop(stack), pop(stack));

        } else if (statementContext.BREAK() != null) {
            return new KxiBreakStatement();

        } else if (statementContext.IF() != null) {
            KxiElseStatement kxiElseStatement;
            AbstractKxiStatement kxiStatement;
            AbstractKxiExpression expression;
            KxiIfStatement kxiIfStatement;
            if (statementContext.ELSE() != null) {
                kxiElseStatement = new KxiElseStatement(pop(stack));
                kxiStatement = pop(stack);
                expression = pop(stack);
                kxiIfStatement = new KxiIfStatement(kxiElseStatement, kxiStatement, expression);
                kxiElseStatement.setParent(kxiIfStatement);
            } else {
                kxiStatement = pop(stack);
                expression = pop(stack);
                kxiIfStatement = new KxiIfStatement(null, kxiStatement, expression);
            }
            kxiStatement.setParent(kxiIfStatement);
            expression.setParent(kxiIfStatement);
            return kxiIfStatement;

        } else if (statementContext.WHILE() != null) {
            return new KxiWhileStatement(pop(stack), pop(stack));

        } else if (statementContext.FOR() != null) {

            AbstractKxiStatement statement = pop(stack);
            KxiPostForExpression postExpression = null;
            AbstractKxiExpression conditionalExpression = null;
            KxiPreForExpression preExpression = null;

            int section = 0;
            List<ParseTree> forChildren = statementContext.children;

            for (int i = forChildren.size() - 1; i >= 0; i--) {
                ParseTree parseTree = forChildren.get(i);
                if (parseTree instanceof KxiParser.ExpressionContext) {
                    if (section == 0) {
                        postExpression = new KxiPostForExpression(pop(stack));
                        postExpression.setLineInfo(buildLineInfo(ctx));

                    } else if (section == 1) {
                        conditionalExpression = pop(stack);
                    } else if (section == 2) {
                        preExpression = new KxiPreForExpression(pop(stack));
                        preExpression.setLineInfo(buildLineInfo(ctx));
                    }
                }
                if (parseTree instanceof TerminalNodeImpl) {
                    if (((TerminalNodeImpl) parseTree).getSymbol().getType() == SEMICOLON) {
                        section += 1;
                    }
                }
            }
            return new KxiForStatement(statement, postExpression, conditionalExpression, preExpression);


        } else if (statementContext.RETURN() != null) {
            if (statementContext.children.get(1) instanceof ExpressionContext)
                return new KxiReturnStatement(pop(stack));
            else
                return new KxiReturnStatement(null);

        } else if (statementContext.COUT() != null) {
            return new KxiCoutStatement(pop(stack));

        } else if (statementContext.CIN() != null) {
            return new KxiCinStatement(pop(stack));

        } else if (statementContext.block() != null) {
            return new KxiBlockStatement(pop(stack));

        }

        //Keep at bottom
        else if (!statementContext.expression().isEmpty() && statementContext.SEMICOLON() != null) {
            return new KxiExpressionStatement(pop(stack));

        }

        return null;
    }

    private KxiBlock getBlock(AbstractKxiStatement statement) {
        if (statement instanceof KxiBlockStatement)
            return ((KxiBlockStatement) statement).getBlock();

        //return a list with 1 item
        List<AbstractKxiStatement> statementList = new ArrayList<>();
        statementList.add(statement);
        return new KxiBlock(new GenericListNode(statementList));
    }
}
