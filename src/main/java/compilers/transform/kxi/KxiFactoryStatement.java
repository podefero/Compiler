package compilers.transform.kxi;

import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.statements.KxiVariableDeclarationStatement;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Stack;

import static compilers.antlr.KxiParser.*;

public class KxiFactoryStatement extends AbstractKxiFactory{
    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Stack<AbstractKxiNode> stack) {
        StatementContext statementContext = (StatementContext) ctx;

        if(((StatementContext) ctx).variableDeclaration() != null) {
            return new KxiVariableDeclarationStatement(pop(stack));
        }

        return super.build(ctx, stack);
    }
}
