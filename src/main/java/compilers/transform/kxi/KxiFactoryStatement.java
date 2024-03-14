package compilers.transform.kxi;

import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.statements.KxiExpressionStatement;
import compilers.ast.kxi_nodes.statements.KxiVariableDeclarationStatement;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Stack;

import static compilers.antlr.KxiParser.*;

public class KxiFactoryStatement extends AbstractKxiFactory{
    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Stack<AbstractKxiNode> stack) {
        StatementContext statementContext = (StatementContext) ctx;

        if(statementContext.variableDeclaration() != null) {
            return new KxiVariableDeclarationStatement(pop(stack));
        } else if (!statementContext.expression().isEmpty() && statementContext.SEMICOLON() != null) {
            return new KxiExpressionStatement(pop(stack));
        }

        return super.build(ctx, stack);
    }
}
