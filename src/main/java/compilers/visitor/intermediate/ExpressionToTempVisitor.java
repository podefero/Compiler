package compilers.visitor.intermediate;

import compilers.ast.kxi_nodes.KxiCaseChar;
import compilers.ast.kxi_nodes.KxiCaseInt;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.ast.kxi_nodes.expressions.KxiPostForExpression;
import compilers.ast.kxi_nodes.expressions.KxiPreForExpression;
import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.expressions.uni.AbstractKxiUniOperation;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniPlus;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.ast.kxi_nodes.statements.KxiCinStatement;
import compilers.ast.kxi_nodes.statements.KxiCoutStatement;
import compilers.ast.kxi_nodes.statements.KxiReturnStatement;
import compilers.ast.kxi_nodes.statements.KxiSwitchStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.ast.kxi_nodes.token_literals.IntLitToken;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionToTempVisitor extends KxiVisitorBase {
    Stack<ExpressionLiteral> valueStack;
    public List<ExpressionIdLit> tempVars;
    int caseCount;

    public ExpressionToTempVisitor() {
        valueStack = new Stack<>();
        tempVars = new ArrayList<>();
        caseCount = 0;
    }


    private void setBinaryAssign(AbstractBinaryAssignmentExpression binaryAssign) {
        setBinaryOp(binaryAssign);
        binaryAssign.setId((ExpressionIdLit) valueStack.pop());
        valueStack.push((ExpressionLiteral) binaryAssign.getExpressionL());
    }

    private void setBinaryOp(AbstractKxiBinaryOperation binaryOp) {
        ExpressionLiteral right = valueStack.pop();
        ExpressionLiteral left = valueStack.pop();

        //set register
        right.setLeft(false);
        left.setLeft(true);

        //create temp var
        ExpressionIdLit tempVariable = new ExpressionIdLit(new IdentifierToken("temp$" + HashString.updateStringHash()));
        binaryOp.setTempId(tempVariable);
        tempVars.add(tempVariable);

        //assign LH and RH
        binaryOp.setExpressionL(left);
        binaryOp.setExpressionR(right);

        valueStack.push(tempVariable);
    }

    private void setUnaryOp(AbstractKxiUniOperation uniOp) {
        //ExpressionLiteral right = valueStack.pop();

        ExpressionIdLit tempVariable = new ExpressionIdLit(new IdentifierToken("temp$" + HashString.updateStringHash()));
        tempVariable.setScalarType(ScalarType.INT);
        uniOp.setTempId(tempVariable);
        tempVars.add(tempVariable);

        //uniOp.setExpression(right);
        valueStack.push(tempVariable);
    }

    @Override
    public void visit(ExpressionBoolLit node) {
        if (node.getTokenLiteral().getValue() == true)
            valueStack.push(new ExpressionIntLit(new IntLitToken("1")));
        else
            valueStack.push(new ExpressionIntLit(new IntLitToken("-1")));
    }

    @Override
    public void visit(ExpressionCharLit node) {
        valueStack.push(node);
    }

    @Override
    public void visit(ExpressionIdLit node) {
        valueStack.push(node);
    }

    @Override
    public void visit(ExpressionIntLit node) {
        valueStack.push(node);
    }

    @Override
    public void visit(ExpressionNullLit node) {
        valueStack.push(new ExpressionIntLit(new IntLitToken("0")));
    }

    @Override
    public void visit(ExpressionStringLit node) {
        ExpressionIdLit expressionIdLit = new ExpressionIdLit(new IdentifierToken("string" + HashString.updateStringHash()));
        expressionIdLit.setScalarType(ScalarType.STRING);
        expressionIdLit.setStringLit(true);
        node.setGlobalVariable(expressionIdLit);
        valueStack.push(node);
    }

//    @Override
//    public void visit(KxiDotExpression node) {
//        InterId interId = (InterId) valueStack.pop();
//        InterPtr interPtr = interId.convertToPtr();
//        valueStack.push(interPtr);
//    }

//    @Override
//    public void visit(KxiMethodExpression node) {
//        int size = node.getArguments().getArguments().size();
//        List<InterExpression> expressions = new ArrayList<>();
//
//        for (int i = 0; i < size; i++) {
//            InterArgs interArgs = new InterArgs(getOperand(false, node.getLineInfo()));
//            expressions.add(interArgs);
//        }
//
//        expressions.add(new InterMethodOperation(null));
//        node.setInterExpressions(expressions);
//        InterId interid = (InterId) valueStack.pop();
//        InterFunctionalCall interFunctionalCall = new InterFunctionalCall(interid, interid.getScalarType());
//        valueStack.push(interFunctionalCall);
//    }

    @Override
    public void visit(KxiMultEquals node) {
        setBinaryAssign(node);
    }

    @Override
    public void visit(KxiDivEquals node) {
        setBinaryAssign(node);
    }

    @Override
    public void visit(KxiPlusEquals node) {
        setBinaryAssign(node);
    }

    @Override
    public void visit(KxiSubtractEquals node) {
        setBinaryAssign(node);
    }

    @Override
    public void visit(KxiMult node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiDiv node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiPlus node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiSubtract node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiLessThen node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiGreaterThen node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiGreaterEqualsThen node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiLessEqualsThen node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiEqualsEquals node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiNotEquals node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiAnd node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiOr node) {
        setBinaryOp(node);
    }

    @Override
    public void visit(KxiNot node) {
        setUnaryOp(node);
    }

    @Override
    public void visit(KxiUniPlus node) {
        valueStack.pop();
        setUnaryOp(node);
    }

    @Override
    public void visit(KxiUniSubtract node) {
        ;
        setUnaryOp(node);
    }

    @Override
    public void visit(KxiVariableDeclaration node) {
        tempVars.add(new ExpressionIdLit(node.getIdToken()));
        if (node.getInitializer() != null) {
            node.setInitializer(valueStack.pop());
        }
    }

    @Override
    public void visit(KxiEquals node) {
        if (valueStack.size() >= 2) {
            ExpressionLiteral right = valueStack.pop();
            ExpressionLiteral left = valueStack.pop();

            //set register
            right.setLeft(false);
            left.setLeft(true);

            node.setExpressionL(left);
            node.setExpressionR(right);

            valueStack.push(left);
        }
    }

    @Override
    public void visit(KxiCoutStatement node) {
        node.setExpression(valueStack.pop());
    }

    @Override
    public void visit(KxiCinStatement node) {
        node.setExpression(valueStack.pop());
    }

    @Override
    public void visit(KxiIfStatement node) {
        node.setConditionalExpression(valueStack.pop());
    }

    @Override
    public void visit(KxiWhileStatement node) {
        node.setConditionalExpression(valueStack.pop());
    }

    @Override
    public void visit(KxiPreForExpression node) {
        node.setExpression(valueStack.pop());
    }

    @Override
    public void visit(KxiPostForExpression node) {
        node.setExpression(valueStack.pop());
    }

    @Override
    public void visit(KxiForStatement node) {
        node.setConditionalExpression(valueStack.pop());
    }

    @Override
    public void visit(KxiReturnStatement node) {
        if (node.getExpression() != null)
            node.setExpression(valueStack.pop());
    }

    @Override
    public void preVisit(KxiCaseBlock node) {
        caseCount = valueStack.size();
    }

    @Override
    public void visit(KxiCaseBlock node) {
        int newCount = valueStack.size();
        int count = newCount - caseCount;
        for(int i = 0; i < count; i++) {
            valueStack.pop();
        }
    }


    @Override
    public void visit(KxiSwitchStatement node) {
        node.setExpression(valueStack.pop());
    }


}
