package compilers.visitor;

import compilers.antlr.KxiParser;
import compilers.antlr.KxiParserVisitor;
import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiCharCase;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.KxiParameter;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.class_members.KxiConstructor;
import compilers.ast.kxi_nodes.class_members.KxiDataMember;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.ast.kxi_nodes.scope.KxiClass;
import compilers.ast.kxi_nodes.statements.KxiBreakStatement;
import compilers.transform.kxi.AbstractKxiFactory;
import compilers.transform.kxi.KxiFactoryExpression;
import compilers.util.KxiParseHelper;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import java.util.ArrayDeque;
import java.util.Queue;

@Getter
public class AntlrToKxiVisitor<Void> extends AbstractParseTreeVisitor<Void> implements KxiParserVisitor<Void> {
    private Queue<AbstractKxiNode> nodeQueue;
    private AbstractKxiNode rootNode;
    private KxiParseHelper parseHelper;

    public AntlrToKxiVisitor() {
        nodeQueue = new ArrayDeque<>();
        parseHelper = new KxiParseHelper();
    }

    private void addToQue(AbstractKxiNode kxiNode) {
        nodeQueue.add(kxiNode);
    }

    private AbstractKxiNode pop() {
        return nodeQueue.poll();
    }

    @Override
    public Void visitCompilationUnit(KxiParser.CompilationUnitContext ctx) {
        visitChildren(ctx);
        addToQue(new KxiMain());
        return null;
    }

    @Override
    public Void visitClassDefinition(KxiParser.ClassDefinitionContext ctx) {
        visitChildren(ctx);
        addToQue(new KxiClass());
        return null;
    }

    @Override
    public Void visitScalarType(KxiParser.ScalarTypeContext ctx) {
        //see visitType
        return null;
    }

    @Override
    public Void visitType(KxiParser.TypeContext ctx) {
        //figure out from parent context
        return null;
    }

    @Override
    public Void visitModifier(KxiParser.ModifierContext ctx) {
        //handled in ClassMember
        return null;
    }

    @Override
    public Void visitClassMemberDefinition(KxiParser.ClassMemberDefinitionContext ctx) {
        visitChildren(ctx);
        //handled in its children
        return null;
    }

    @Override
    public Void visitDataMemberDeclaration(KxiParser.DataMemberDeclarationContext ctx) {
        visitChildren(ctx);
        addToQue(new KxiDataMember());
        return null;
    }

    @Override
    public Void visitMethodDeclaration(KxiParser.MethodDeclarationContext ctx) {
        visitChildren(ctx);
        addToQue(new KxiMethod());
        return null;
    }

    @Override
    public Void visitConstructorDeclaration(KxiParser.ConstructorDeclarationContext ctx) {
        visitChildren(ctx);
        addToQue(new KxiConstructor());
        return null;
    }

    @Override
    public Void visitMethodSuffix(KxiParser.MethodSuffixContext ctx) {
        visitChildren(ctx);
        //handled in parent nodes
        return null;
    }

    @Override
    public Void visitParameterList(KxiParser.ParameterListContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitParameter(KxiParser.ParameterContext ctx) {
        //don't need to visit children here
        addToQue(new KxiParameter());
        return null;
    }

    @Override
    public Void visitVariableDeclaration(KxiParser.VariableDeclarationContext ctx) {
        visitChildren(ctx);
        addToQue(new KxiVariableDeclaration());
        return null;
    }

    @Override
    public Void visitInitializer(KxiParser.InitializerContext ctx) {
        visitChildren(ctx);
        //don't need this on stack
        return null;
    }

    @Override
    public Void visitStatement(KxiParser.StatementContext ctx) {
        visitChildren(ctx);
        //logic to handle which statement class based on context
        addToQue(new KxiBreakStatement()); //placeholder
        return null;
    }

    @Override
    public Void visitBlock(KxiParser.BlockContext ctx) {
        visitChildren(ctx);
        addToQue(new KxiBlock());
        return null;
    }

    @Override
    public Void visitCaseBlock(KxiParser.CaseBlockContext ctx) {
        visitChildren(ctx);
        addToQue(new KxiCaseBlock());
        return null;
    }

    @Override
    public Void visitCase(KxiParser.CaseContext ctx) {
        visitChildren(ctx);
        addToQue(new KxiCharCase()); //placeholder
        return null;
    }

    @Override
    public Void visitExpression(KxiParser.ExpressionContext ctx) {
        visitChildren(ctx);
        KxiFactoryExpression factoryExpression = new KxiFactoryExpression(ctx, nodeQueue);
        addToQue(factoryExpression.build());
        return null;
    }

    @Override
    public Void visitArguments(KxiParser.ArgumentsContext ctx) {
        //handled in expression
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitArgumentList(KxiParser.ArgumentListContext ctx) {
        //handled in expression
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitIndex(KxiParser.IndexContext ctx) {
        //handled in expression
        visitChildren(ctx);
        return null;
    }
}
