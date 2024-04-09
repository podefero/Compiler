package compilers.transform.kxi;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static compilers.antlr.KxiParser.ExpressionContext;
import static compilers.antlr.KxiParser.StatementContext;

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
            if (statementContext.ELSE() != null)
                return new KxiIfStatement(getBlock(pop(stack)), getBlock(pop(stack)), pop(stack));
            else
                return new KxiIfStatement(null, getBlock(pop(stack)), pop(stack));

        } else if (statementContext.WHILE() != null) {
            return new KxiWhileStatement(getBlock(pop(stack)), pop(stack));

        } else if (statementContext.FOR() != null) {
            AbstractKxiStatement statement = pop(stack);
            AbstractKxiExpression postExpression = null;
            AbstractKxiExpression conditionalExpression;
            AbstractKxiExpression preExpression = null;

            if (statementContext.children.get(6) instanceof ExpressionContext)
                postExpression = pop(stack);

            conditionalExpression = pop(stack);

            if (statementContext.children.get(2) instanceof ExpressionContext)
                preExpression = pop(stack);

            return new KxiForStatement(getBlock(statement), postExpression, conditionalExpression, preExpression);


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

        return super.build(ctx, stack);
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
