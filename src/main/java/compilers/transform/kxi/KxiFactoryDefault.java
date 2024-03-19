package compilers.transform.kxi;

import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.Modifier;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.class_members.KxiConstructor;
import compilers.ast.kxi_nodes.class_members.KxiDataMember;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.token_expression.CharLitToken;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.expressions.token_expression.IntLitToken;
import compilers.ast.kxi_nodes.other.KxiInvalidNode;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;
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
                    , new IdentifierToken(getTokenText(varCtx.IDENTIFIER()))
                    , pop(stack));

        } else if (ctx instanceof BlockContext) {
            return new KxiBlock(popList(stack, getListSizeFromCtx(((BlockContext) ctx).statement())));

        } else if (ctx instanceof CompilationUnitContext) {
            return new KxiMain(pop(stack)
                    , new IdentifierToken(getTokenText(((CompilationUnitContext) ctx).IDENTIFIER()))
                    , popList(stack, getListSizeFromCtx(((CompilationUnitContext) ctx).classDefinition())));

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
            return new KxiClass(popList(stack, getListSizeFromCtx(((ClassDefinitionContext) ctx).classMemberDefinition()))
                    , new IdentifierToken(getTokenText(((ClassDefinitionContext) ctx).IDENTIFIER())));

        } else if (ctx instanceof DataMemberDeclarationContext) {
            boolean isStatic = ((DataMemberDeclarationContext) ctx).STATIC() != null;
            return new KxiDataMember(pop(stack), ((KxiModifierHelper) pop(stack)).getModifier(), isStatic);

        } else if (ctx instanceof MethodDeclarationContext) {
            boolean isStatic;
            isStatic = ((MethodDeclarationContext) ctx).STATIC() != null;
            KxiMethodSuffixHelper methodSuffixHelper = pop(stack);
            KxiType type = pop(stack);
            KxiModifierHelper modifierHelper = pop(stack);

            return new KxiMethod(methodSuffixHelper.getBlock(), methodSuffixHelper.getParameters(), methodSuffixHelper.getId(), type, modifierHelper.getModifier(), isStatic);

        } else if (ctx instanceof ConstructorDeclarationContext) {
            KxiMethodSuffixHelper methodSuffixHelper = pop(stack);
            return new KxiConstructor(methodSuffixHelper.getBlock(), methodSuffixHelper.getParameters(), methodSuffixHelper.getId());

        } else if (ctx instanceof MethodSuffixContext) {
            MethodSuffixContext methodSuffixContext = (MethodSuffixContext) ctx;
            KxiBlock block = pop(stack);
            Optional<List<KxiParameter>> parameterList;
            if (methodSuffixContext.parameterList() != null) {
                parameterList = Optional.of(popList(stack, getListSizeFromCtx(methodSuffixContext.parameterList().parameter())));
            } else {
                parameterList = Optional.empty();
            }
            return new KxiMethodSuffixHelper(block, parameterList, new IdentifierToken(getTokenText(methodSuffixContext.IDENTIFIER())));

        } else if (ctx instanceof ModifierContext) {
            ModifierContext modifierContext = (ModifierContext) ctx;
            Modifier modifier;
            if (modifierContext.PRIVATE() != null) modifier = Modifier.PRIVATE;
            else modifier = Modifier.PUBLIC;
            return new KxiModifierHelper(modifier);

        } else if (ctx instanceof ParameterContext) {
            return new KxiParameter(new IdentifierToken(getTokenText(((ParameterContext) ctx).IDENTIFIER())), pop(stack));

        } else if (ctx instanceof CaseBlockContext) {
            CaseBlockContext caseBlockContext = (CaseBlockContext) ctx;
            return new KxiCaseBlock(popList(stack, getListSizeFromCtx(caseBlockContext.statement()))
                    , popList(stack, getListSizeFromCtx(caseBlockContext.case_())));

        } else if (ctx instanceof CaseContext) {
            CaseContext caseContext = (CaseContext) ctx;
            if (caseContext.INTLIT() != null)
                return new KxiCaseInt(popList(stack, getListSizeFromCtx(caseContext.statement()))
                        , new IntLitToken(getTokenText(caseContext.INTLIT())));
            else
                return new KxiCaseChar(popList(stack, getListSizeFromCtx(caseContext.statement()))
                        , new CharLitToken(getTokenText(caseContext.CHARLIT())));

        }
        return new KxiInvalidNode(ctx, stack, null);
    }
}
