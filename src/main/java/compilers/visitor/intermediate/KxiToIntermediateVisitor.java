package compilers.visitor.intermediate;

import compilers.ast.intermediate.*;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.class_members.KxiConstructor;
import compilers.ast.kxi_nodes.class_members.KxiDataMember;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.KxiDotExpression;
import compilers.ast.kxi_nodes.expressions.KxiExpressionIndex;
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
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.transform.intermediate.AbstractInterFactory;
import compilers.transform.intermediate.InterFactoryDefault;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;
import java.util.Stack;

@Getter
public class KxiToIntermediateVisitor extends KxiVisitorBase {
    private final Stack<AbstractInterNode> nodeStack;
    private AbstractInterNode rootNode;
    private List<InterGlobalVariable> globalVariableList;

    public KxiToIntermediateVisitor() {
        nodeStack = new Stack<>();
    }



    @Override
    public void visit(KxiMain node) {
    }

    @Override
    public void visit(KxiMethod node) {
    }

    @Override
    public void visit(KxiReturnStatement node) {
    }

    @Override
    public void visit(KxiDiv node) {
    }
    @Override
    public void visit(KxiMult node) {
    }
    @Override
    public void visit(KxiPlus node) {
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
    @Override
    public void visit(KxiForStatement node) {
    }
    @Override
    public void visit(KxiIfStatement node) {
    }
    @Override
    public void visit(KxiWhileStatement node) {
    }
    @Override
    public void visit(KxiBreakStatement node) {
    }
    @Override
    public void visit(KxiCinStatement node) {
    }
    @Override
    public void visit(KxiCoutStatement node) {
    }
    @Override
    public void visit(KxiExpressionStatement node) {
    }
    @Override
    public void visit(KxiSwitchStatement node) {
    }
    @Override
    public void visit(KxiVariableDeclaration node) {
    } @Override
    public void visit(KxiArguments node) {
    }
    @Override
    public void visit(KxiParameter node) {
    }
    @Override
    public void visit(KxiCaseChar node) {
    }
    @Override
    public void visit(KxiCaseInt node) {
    }
    @Override
    public void visit(KxiConstructor node) {
    }
    @Override
    public void visit(KxiDataMember node) {
    }

}
