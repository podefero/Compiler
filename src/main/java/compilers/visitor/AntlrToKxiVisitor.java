package compilers.visitor;

import compilers.antlr.KxiParser;
import compilers.antlr.KxiParserVisitor;
import compilers.ast.AbstractKxiNode;
import compilers.ast.kxi_nodes.other.KxiInvalidNode;
import compilers.transform.kxi.AbstractKxiFactory;
import compilers.transform.kxi.KxiFactoryBase;
import compilers.util.KxiParseHelper;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import java.util.Stack;

@Getter
public class AntlrToKxiVisitor<Void> extends AbstractParseTreeVisitor<Void> implements KxiParserVisitor<Void> {
    private final Stack<AbstractKxiNode> nodeStack;
    private AbstractKxiNode rootNode;
    private final KxiParseHelper parseHelper;
    private final AbstractKxiFactory factory;

    public AntlrToKxiVisitor() {
        nodeStack = new Stack<>();
        parseHelper = new KxiParseHelper();
        factory = new KxiFactoryBase();
    }

    private void transformNode(ParserRuleContext ctx) {
        nodeStack.push(factory.build(ctx, nodeStack));
//        try {
//            nodeStack.push(factory.build(ctx, nodeStack));
//        } catch (ClassCastException ex) {
//            nodeStack.push(new KxiInvalidNode(ctx, nodeStack, ex));
//        }
    }

    @Override
    public Void visitCompilationUnit(KxiParser.CompilationUnitContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
        rootNode = nodeStack.pop();
        return null;
    }

    @Override
    public Void visitClassDefinition(KxiParser.ClassDefinitionContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
        return null;
    }

    @Override
    public Void visitScalarType(KxiParser.ScalarTypeContext ctx) {
        //see visitType
        return null;
    }

    @Override
    public Void visitType(KxiParser.TypeContext ctx) {
        transformNode(ctx);
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
        transformNode(ctx);
        return null;
    }

    @Override
    public Void visitMethodDeclaration(KxiParser.MethodDeclarationContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
        return null;
    }

    @Override
    public Void visitConstructorDeclaration(KxiParser.ConstructorDeclarationContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
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
        visitChildren(ctx);
        transformNode(ctx);
        return null;
    }

    @Override
    public Void visitVariableDeclaration(KxiParser.VariableDeclarationContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
        return null;
    }

    @Override
    public Void visitInitializer(KxiParser.InitializerContext ctx) {
        visitChildren(ctx);
        return null;
    }

    @Override
    public Void visitStatement(KxiParser.StatementContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
        return null;
    }

    @Override
    public Void visitBlock(KxiParser.BlockContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
        return null;
    }

    @Override
    public Void visitCaseBlock(KxiParser.CaseBlockContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
        return null;
    }

    @Override
    public Void visitCase(KxiParser.CaseContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
        return null;
    }

    @Override
    public Void visitExpression(KxiParser.ExpressionContext ctx) {
        visitChildren(ctx);
        transformNode(ctx);
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
