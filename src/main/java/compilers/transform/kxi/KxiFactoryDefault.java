package compilers.transform.kxi;

import compilers.ast.AbstractKxiNode;
import compilers.ast.Modifier;
import compilers.ast.ScalarType;
import compilers.ast.kxi_nodes.*;
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
            DataMemberDeclarationContext dataMemberDeclarationContext = (DataMemberDeclarationContext) ctx;
            Modifier modifier;
            boolean isStatic = false;
            if (dataMemberDeclarationContext.modifier().PRIVATE() != null) modifier = Modifier.PRIVATE;
            else modifier = Modifier.PUBLIC;
            if (dataMemberDeclarationContext.STATIC() != null) isStatic = true;
            return new KxiDataMember(pop(stack), modifier, isStatic);

        } else if (ctx instanceof MethodDeclarationContext) {
            MethodDeclarationContext methodDeclarationContext = (MethodDeclarationContext) ctx;
            Modifier modifier;
            boolean isStatic = false;
            if (methodDeclarationContext.modifier().PRIVATE() != null) modifier = Modifier.PRIVATE;
            else modifier = Modifier.PUBLIC;
            if (methodDeclarationContext.STATIC() != null) isStatic = true;
            int size = 0;
            KxiBlock block = pop(stack);
            Optional<List<KxiParameter>> parameterList;
            if (methodDeclarationContext.methodSuffix().parameterList() != null) {
                parameterList = Optional.of(popList(stack, getListSizeFromCtx(methodDeclarationContext.methodSuffix().parameterList().parameter())));
            } else {
                parameterList = Optional.empty();
            }
            return new KxiMethod(block, parameterList, pop(stack), modifier, isStatic);

        } else if (ctx instanceof ParameterContext) {
            return new KxiParameter(new IdentifierToken(getTokenText(((ParameterContext) ctx).IDENTIFIER())), pop(stack));

        } else if (ctx instanceof IndexContext) {
            return new KxiIndex(pop(stack));

        } else if (ctx instanceof CaseBlockContext) {
            CaseBlockContext caseBlockContext = (CaseBlockContext) ctx;
            return new KxiCaseBlock(popList(stack, getListSizeFromCtx(caseBlockContext.statement()))
                    , popList(stack, getListSizeFromCtx(caseBlockContext.case_())));

        } else if (ctx instanceof CaseContext) {
            CaseContext caseContext = (CaseContext) ctx;
            if (caseContext.INTLIT() != null)
                return new KxiCase<>(popList(stack, getListSizeFromCtx(caseContext.statement()))
                        , new IntLitToken(getTokenText(caseContext.INTLIT())));
            else
                return new KxiCase<>(popList(stack, getListSizeFromCtx(caseContext.statement()))
                        , new CharLitToken(getTokenText(caseContext.CHARLIT())));

        }
        return new KxiInvalidNode(ctx,stack, null);
    }
}
