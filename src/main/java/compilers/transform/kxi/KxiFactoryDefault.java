package compilers.transform.kxi;

import compilers.ast.AbstractKxiNode;
import compilers.ast.Modifier;
import compilers.ast.ScalarType;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.class_members.KxiDataMember;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.other.KxiInvalidNode;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;
import java.util.Stack;

import static compilers.antlr.KxiParser.*;

public class KxiFactoryDefault extends AbstractKxiFactory {
    @Override
    public AbstractKxiNode build(ParserRuleContext ctx, Stack<AbstractKxiNode> stack) {
        if (ctx instanceof VariableDeclarationContext) {
            VariableDeclarationContext varCtx = (VariableDeclarationContext) ctx;
            Optional<AbstractKxiExpression> expression;

            if (varCtx.initializer() != null) expression = Optional.of(pop(stack));
            else expression = Optional.empty();

            return new KxiVariableDeclaration(expression
                    , new IdentifierToken(getTokenText(varCtx.IDENTIFIER()), ScalarType.ID)
                    , pop(stack));

        } else if (ctx instanceof BlockContext) {
            int size = ((BlockContext) ctx).statement().size();
            return new KxiBlock(popList(stack, size));

        } else if (ctx instanceof CompilationUnitContext) {
            int size = ((CompilationUnitContext) ctx).classDefinition().size();
            return new KxiMain(pop(stack)
                    , new IdentifierToken(getTokenText(((CompilationUnitContext) ctx).IDENTIFIER()), ScalarType.ID)
                    , popList(stack, size));

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

        } else if (ctx instanceof ClassDefinitionContext) {
            int size = ((ClassDefinitionContext) ctx).classMemberDefinition().size();
            return new KxiClass(popList(stack, size)
                    , new IdentifierToken(getTokenText(((ClassDefinitionContext) ctx).IDENTIFIER()), ScalarType.ID));
        } else if (ctx instanceof DataMemberDeclarationContext) {
            DataMemberDeclarationContext dataMemberDeclarationContext = (DataMemberDeclarationContext) ctx;
            Modifier modifier;
            boolean isStatic = false;
            if (dataMemberDeclarationContext.modifier().PRIVATE() != null) modifier = Modifier.PRIVATE;
            else modifier = Modifier.PUBLIC;
            if (dataMemberDeclarationContext.STATIC() != null) isStatic = true;
            return new KxiDataMember(pop(stack), modifier, isStatic);
        }
        return new KxiInvalidNode(Optional.ofNullable(ctx), Optional.ofNullable(stack), Optional.empty());
    }
}
