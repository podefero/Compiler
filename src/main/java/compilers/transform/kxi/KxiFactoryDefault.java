package compilers.transform.kxi;

import compilers.ast.AbstractKxiNode;
import compilers.ast.ScalarType;
import compilers.ast.kxi_nodes.KxiInvalidNode;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;
import java.util.Stack;

import static compilers.antlr.KxiParser.*;

public class KxiFactoryDefault extends AbstractKxiFactory {
    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Stack<AbstractKxiNode> stack) {
        if (ctx instanceof VariableDeclarationContext)
            return new KxiVariableDeclaration(pop(stack)
                    , new IdentifierToken(getTokenText(((VariableDeclarationContext) ctx).IDENTIFIER()))
                    , Optional.ofNullable(pop(stack)));

        else if (ctx instanceof BlockContext) {
            return new KxiBlock(popList(stack, AbstractKxiStatement.class));

        } else if (ctx instanceof CompilationUnitContext) {
            return new KxiMain(new IdentifierToken(getTokenText(((CompilationUnitContext) ctx).IDENTIFIER()))
                    , popList(stack, KxiClass.class)
                    , pop(stack));

        } else if (ctx instanceof TypeContext) {
            ScalarType scalarType = ScalarType.UNKNOWN;
            ScalarTypeContext scalarTypeContext = ((TypeContext) ctx).scalarType();
            int numArrays = ((TypeContext) ctx).LBRACKET().size();
            if (scalarTypeContext.VOID() != null) scalarType = ScalarType.VOID;
            else if (scalarTypeContext.INT() != null) scalarType = ScalarType.INT;
            else if (scalarTypeContext.CHAR_KEY() != null) scalarType = ScalarType.CHAR;
            else if (scalarTypeContext.BOOL() != null) scalarType = ScalarType.BOOL;
            else if (scalarTypeContext.STRING() != null) scalarType = ScalarType.STRING;
            else if (scalarTypeContext.IDENTIFIER() != null) scalarType = ScalarType.ID;
            return new KxiType(scalarType, numArrays);
        }
        return new KxiInvalidNode(Optional.ofNullable(ctx), Optional.ofNullable(stack), Optional.empty());
    }
}
