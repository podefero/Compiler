package compilers.visitor.intermediate;

import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.KxiDotExpression;
import compilers.ast.kxi_nodes.expressions.KxiMethodExpression;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniPlus;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.statements.KxiReturnStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.symboltable.BlockScope;
import compilers.visitor.kxi.symboltable.ScopeType;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Stack;

@AllArgsConstructor
public class KxiSimplifyVisitor extends KxiVisitorBase {

    Stack<AbstractKxiNode> kxiNodeStack;

    private boolean hasNode(Class<? extends AbstractKxiNode> node, List<? extends AbstractKxiNode> list) {
        for (AbstractKxiNode abstractKxiNode : list) {
            if (abstractKxiNode.getClass() == node) {
                return true;
            }
        }
        return false;
    }

    private boolean areLiterals(AbstractKxiNode... nodes) {

        for (AbstractKxiNode node : nodes) {
            if (!(node instanceof ExpressionLiteral)) {
                return false;
            }
        }
        return true;

    }

    @Override
    public void visit(KxiMain node) {
        String id = node.getId().getValue();
        node.getId().setValue(currentScope.getUniqueName() + id);
//        //make main into a function node
//        KxiMethod kxiMethod = new KxiMethod(new KxiMethodSuffixHelper(node.getBlock(), new GenericListNode(new ArrayList<>()), node.getId())
//                , new KxiType(ScalarType.VOID, null)
//                , new KxiModifierHelper(Modifier.PUBLIC)
//                , new KxiStaticHelper(false));
//        node.getChildren().add(0, kxiMethod);

    }

    @Override
    public void visit(KxiMethod node) {
        String id = node.getId().getValue();
        node.getId().setValue(currentScope.getUniqueName() + id);
    }

    @Override
    public void visit(KxiBlock node) {
        //add return statements to methods and main (if they don't have one)
        BlockScope blockScope = (BlockScope) node.getScope();
        if ((blockScope.getScopeType() == ScopeType.Method || blockScope.getScopeType() == ScopeType.Main)
                && !hasNode(KxiReturnStatement.class, node.getStatements())) {
            node.getChildren().add(new KxiReturnStatement(null));
        }
    }

    @Override
    public void visit(KxiVariableDeclaration node) {
        String id = node.getId().getValue();
        node.getId().setValue(currentScope.getUniqueName() + id);
    }

    @Override
    public void visit(KxiDiv node) {
    }

    @Override
    public void visit(KxiMult node) {
    }

    @Override
    public void visit(KxiPlus node) {
//        if (!areLiterals(node.getExpressionL(), node.getExpressionR())) {
//            AbstractKxiExpression expression = (AbstractKxiExpression) kxiNodeStack.pop();
//            IdentifierToken identifierToken = new IdentifierToken("" + node.hashCode());
//
//            KxiVariableDeclaration kxiVariableDeclaration =
//                    new KxiVariableDeclaration(expression
//                            , identifierToken
//                            , new KxiType(ScalarType.INT, null));
//
//            node.getChildren().add(kxiVariableDeclaration);
//
//        }
//        kxiNodeStack.push(node);

    }

    @Override
    public void visit(KxiSubtract node) {
    }

    @Override
    public void visit(KxiDivEquals node) {
    }

    @Override
    public void visit(KxiEquals node) {

    }

    @Override
    public void visit(KxiMultEquals node) {
    }

    @Override
    public void visit(KxiPlusEquals node) {
    }

    @Override
    public void visit(KxiSubtractEquals node) {
    }

    @Override
    public void visit(KxiAnd node) {
    }

    @Override
    public void visit(KxiEqualsEquals node) {
    }

    @Override
    public void visit(KxiGreaterEqualsThen node) {
    }

    @Override
    public void visit(KxiGreaterThen node) {
    }

    @Override
    public void visit(KxiLessEqualsThen node) {
    }

    @Override
    public void visit(KxiLessThen node) {
    }

    @Override
    public void visit(KxiNotEquals node) {
    }

    @Override
    public void visit(KxiOr node) {
    }

    @Override
    public void visit(ExpressionBoolLit node) {
    }

    @Override
    public void visit(ExpressionCharLit node) {
    }

    @Override
    public void visit(ExpressionIdLit node) {
        String id = node.getTokenLiteral().getValue();
        node.getTokenLiteral().setValue(currentScope.getUniqueName() + id);
    }

    @Override
    public void visit(ExpressionIntLit node) {
    }

    @Override
    public void visit(ExpressionNullLit node) {
    }

    @Override
    public void visit(ExpressionStringLit node) {
    }

    @Override
    public void visit(ExpressionThisLit node) {
    }

    @Override
    public void visit(KxiNot node) {
    }

    @Override
    public void visit(KxiUniPlus node) {
    }

    @Override
    public void visit(KxiUniSubtract node) {
    }

    @Override
    public void visit(KxiDotExpression node) {
    }

    @Override
    public void visit(KxiMethodExpression node) {
    }


}
