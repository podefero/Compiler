package compilers.visitor.intermediate;

import compilers.ast.kxi_nodes.*;
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
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
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

//    @Override
//    public void visit(KxiMain node) {
//        String id = node.getId().getValue();
//        node.getId().setValue(currentScope.getUniqueName() + id);
//    }

//    @Override
//    public void visit(KxiMethod node) {
//        String id = node.getId().getValue();
//        node.getId().setValue(currentScope.getUniqueName() + id);
//    }

    @Override
    public void visit(KxiBlock node) {
        //add return statements to methods and main (if they don't have one)
        BlockScope blockScope = (BlockScope) node.getScope();
        if ((blockScope.getScopeType() == ScopeType.Method || blockScope.getScopeType() == ScopeType.Main)
                && !hasNode(KxiReturnStatement.class, node.getStatements())) {
            node.getChildren().add(new KxiReturnStatement(null));
        }
    }

//    @Override
//    public void visit(KxiVariableDeclaration node) {
//        String id = node.getId().getValue();
//        node.getId().setValue(currentScope.getUniqueName() + id);
//    }
//
//    @Override
//    public void visit(ExpressionIdLit node) {
//        String id = node.getTokenLiteral().getValue();
//        node.getTokenLiteral().setValue(currentScope.getUniqueName() + id);
//    }
}
