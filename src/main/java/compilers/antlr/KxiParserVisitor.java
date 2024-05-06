// Generated from /home/nag/Documents/UVU/Spring2024/Compiler/CompilerJava/src/main/java/compilers/antlr/KxiParser.g4 by ANTLR 4.13.1
package compilers.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link KxiParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface KxiParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link KxiParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(KxiParser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#classDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDefinition(KxiParser.ClassDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#scalarType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScalarType(KxiParser.ScalarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(KxiParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(KxiParser.ModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#classMemberDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassMemberDefinition(KxiParser.ClassMemberDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#dataMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataMemberDeclaration(KxiParser.DataMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(KxiParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(KxiParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#methodSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodSuffix(KxiParser.MethodSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(KxiParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(KxiParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(KxiParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#initializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializer(KxiParser.InitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(KxiParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(KxiParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#caseBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseBlock(KxiParser.CaseBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#case}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase(KxiParser.CaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(KxiParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(KxiParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(KxiParser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link KxiParser#index}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(KxiParser.IndexContext ctx);
}