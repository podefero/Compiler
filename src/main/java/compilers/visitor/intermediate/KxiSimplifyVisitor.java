package compilers.visitor.intermediate;

import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
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
import compilers.ast.kxi_nodes.statements.KxiReturnStatement;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
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
        if (!hasNode(KxiReturnStatement.class, node.getBlock().getStatements())) {
            node.getBlock().getChildren().add(new KxiReturnStatement(null));
        }
    }


    @Override
    public void visit(KxiDiv node) {
    }

    @Override
    public void visit(KxiMult node) {
    }

    @Override
    public void visit(KxiPlus node) {
        if (!areLiterals(node.getExpressionL(), node.getExpressionR())) {
            AbstractKxiExpression expression = (AbstractKxiExpression) kxiNodeStack.pop();
            IdentifierToken identifierToken = new IdentifierToken(currentScope.getUniqueName() + node.hashCode());

            KxiVariableDeclaration kxiVariableDeclaration =
                    new KxiVariableDeclaration(expression
                            , identifierToken
                            , new KxiType(ScalarType.INT, identifierToken));

            node.getChildren().add(kxiVariableDeclaration);


        }
        kxiNodeStack.push(node);

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
