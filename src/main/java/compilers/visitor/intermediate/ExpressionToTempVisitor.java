package compilers.visitor.intermediate;

import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.*;
import compilers.ast.intermediate.InterOperand.*;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.*;
import compilers.ast.intermediate.statements.InterCoutStatement;
import compilers.ast.intermediate.statements.InterFunctionalCall;
import compilers.ast.intermediate.expression.InterVariable;
import compilers.ast.intermediate.statements.InterGlobalVariable;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.expressions.KxiDotExpression;
import compilers.ast.kxi_nodes.expressions.KxiMethodExpression;
import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.*;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.expressions.uni.AbstractKxiUniOperation;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniPlus;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.visitor.kxi.KxiVisitorBase;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionToTempVisitor extends KxiVisitorBase {
    Stack<InterValue> valueStack;

    public ExpressionToTempVisitor() {
        valueStack = new Stack<>();
    }

    private <T extends InterOperation> InterOperation getBinaryOperation(String error, Class<T> obj) {
        try {
            return obj.getDeclaredConstructor(InterOperand.class, InterOperand.class).newInstance(getOperand(false, error)
                    , getOperand(true, error));
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private InterOperand getOperand(boolean left, String error) {
        if (valueStack.empty()) {
            System.out.println("Can't get operand, empty stack " + error);
            return null;
        }

        InterValue interValue = valueStack.pop();
        interValue = (InterValue) interValue.copy();
        InterOperand operand;

        if (!left) {
            if (interValue instanceof InterId) {
                operand = new RightVariableStack(interValue);
            } else if (interValue instanceof InterPtr)
                operand = new RightPtr(interValue);
            else if (interValue instanceof InterFunctionalCall)
                operand = new OperandReturn(interValue, false);
            else
                operand = new RightOperandLit(interValue);
        } else {
            if (interValue instanceof InterId) {
                operand = new LeftVariableStack(interValue);
            } else if (interValue instanceof InterPtr)
                operand = new LeftPtr(interValue);
            else if (interValue instanceof InterFunctionalCall)
                operand = new OperandReturn(interValue, true);
            else
                operand = new LeftOperandLit(interValue);
        }

        return operand;
    }

    private InterOperand getOperand(boolean left, InterValue interValue) {
        InterOperand operand;
        interValue = (InterValue) interValue.copy();

        if (!left) {
            if (interValue instanceof InterId) {
                operand = new RightVariableStack(interValue);
            } else if (interValue instanceof InterPtr)
                operand = new RightPtr(interValue);
            else if (interValue instanceof InterFunctionalCall)
                operand = new OperandReturn(interValue, false);
            else
                operand = new RightOperandLit(interValue);
        } else {
            if (interValue instanceof InterId) {
                operand = new LeftVariableStack(interValue);
            } else if (interValue instanceof InterPtr)
                operand = new LeftPtr(interValue);
            else if (interValue instanceof InterFunctionalCall)
                operand = new OperandReturn(interValue, true);
            else
                operand = new LeftOperandLit(interValue);
        }
        return operand;
    }

    private void setBinaryAssign(AbstractBinaryAssignmentExpression binaryAssign) {
        InterOperand variable = binaryAssign.getInterOperation().getLeftOperand();
        InterOperand tempVariable = getOperand(false, binaryAssign.getInterVariable().getInterId());
        InterAssignment interAssignment = new InterAssignment(tempVariable, (InterOperand) variable.copy());
        binaryAssign.setInterAssignment(interAssignment);
        valueStack.push(variable.getInterValue());
    }

    private void setBinaryOp(InterOperation interOperation, AbstractKxiBinaryOperation binaryOp) {
        InterId interId = new InterId(ScalarType.INT);
        InterVariable interVariable = new InterVariable(interId, interOperation);
        binaryOp.setInterVariable(interVariable);
        binaryOp.setInterOperation(interOperation);
        valueStack.push(interId);
    }

    private void setUnaryOp(InterOperation interOperation, AbstractKxiUniOperation binaryOp) {
        InterId interId = new InterId(ScalarType.INT);
        InterVariable interVariable = new InterVariable(interId, interOperation);
        binaryOp.setInterVariable(interVariable);
        binaryOp.setInterOperation(interOperation);
        valueStack.push(interId);
    }

    private void setBinaryOpNoPush(InterOperation interOperation, AbstractKxiBinaryOperation binaryOp) {
        InterId interId = new InterId(ScalarType.INT);
        InterVariable interVariable = new InterVariable(interId, interOperation);
        binaryOp.setInterVariable(interVariable);
        binaryOp.setInterOperation(interOperation);
    }

    @Override
    public void visit(ExpressionBoolLit node) {
        if (node.getTokenLiteral().getValue() == true)
            valueStack.push(new InterLit<>(1, ScalarType.INT));
        else
            valueStack.push(new InterLit<>(-1, ScalarType.INT));
    }

    @Override
    public void visit(ExpressionCharLit node) {
        valueStack.push(new InterLit<>(node.getTokenLiteral().getValue(), ScalarType.CHAR));
    }

    @Override
    public void visit(ExpressionIdLit node) {
        valueStack.push(new InterId(node.getId(), node.getScalarType()));
    }

    @Override
    public void visit(ExpressionIntLit node) {
        valueStack.push(new InterLit<>(node.getTokenLiteral().getValue(), ScalarType.INT));
    }

    @Override
    public void visit(ExpressionNullLit node) {
        valueStack.push(new InterLit<>(0, ScalarType.INT));
    }

    @Override
    public void visit(ExpressionStringLit node) {
        InterPtr interId = new InterPtr(ScalarType.STRING);
        InterGlobalVariable interGlobalVariable = new InterGlobalVariable(interId, Directive.STR
                , new InterLit(node.getTokenLiteral().getValue(), ScalarType.STRING));
        valueStack.push(interId);
        node.setGlobalVariable(interGlobalVariable);
    }

//    @Override
//    public void visit(KxiDotExpression node) {
//        InterId interId = (InterId) valueStack.pop();
//        InterPtr interPtr = interId.convertToPtr();
//        valueStack.push(interPtr);
//    }

    @Override
    public void visit(KxiMethodExpression node) {
        int size = node.getArguments().getArguments().size();
        List<InterExpression> expressions = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            InterArgs interArgs = new InterArgs(getOperand(false, node.getLineInfo()));
            expressions.add(interArgs);
        }

        expressions.add(new InterMethodOperation(null));
        node.setInterExpressions(expressions);
        InterId interid = (InterId) valueStack.pop();
        InterFunctionalCall interFunctionalCall = new InterFunctionalCall(interid, interid.getScalarType());
        valueStack.push(interFunctionalCall);
    }

    @Override
    public void visit(KxiMultEquals node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterBinaryMult.class);
        setBinaryOpNoPush(interOperation, node);
        setBinaryAssign(node);
    }

    @Override
    public void visit(KxiDivEquals node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterBinaryDivide.class);
        setBinaryOpNoPush(interOperation, node);
        setBinaryAssign(node);
    }

    @Override
    public void visit(KxiPlusEquals node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterBinaryPlus.class);
        setBinaryOpNoPush(interOperation, node);
        setBinaryAssign(node);
    }

    @Override
    public void visit(KxiSubtractEquals node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterBinarySubtract.class);
        setBinaryOpNoPush(interOperation, node);
        setBinaryAssign(node);
    }

    @Override
    public void visit(KxiMult node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterBinaryMult.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiDiv node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterBinaryDivide.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiPlus node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterBinaryPlus.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiSubtract node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterBinarySubtract.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiLessThen node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterLogicalLessThen.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiGreaterThen node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterLogicalGreaterThen.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiGreaterEqualsThen node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterLogicalGreaterThen.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiLessEqualsThen node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterLogicalLessThen.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiEqualsEquals node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterLogicalEqualsEquals.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiNotEquals node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterLogicalNotEquals.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiAnd node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterLogicalAnd.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiOr node) {
        InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterLogicalOr.class);
        setBinaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiNot node) {
        InterOperation interOperation =
                new InterLogicalNot(new LeftOperandLit(new InterLit<>(-1, ScalarType.INT)), getOperand(false, node.getLineInfo()));
        setUnaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiUniPlus node) {
        valueStack.pop();
        InterOperation interOperation =
                new InterEmptyOperator(null, null);
        setUnaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiUniSubtract node) {
        InterOperand rightOperand = getOperand(false, node.getLineInfo());
        InterOperation interOperation =
                new InterUnarySubOperator(null, rightOperand);
        setUnaryOp(interOperation, node);
    }

    @Override
    public void visit(KxiVariableDeclaration node) {
        if (node.getInitializer() != null) {
            node.setInterInit(getOperand(false, node.getLineInfo()));
        }
    }

    @Override
    public void visit(KxiEquals node) {
        if (valueStack.size() >= 2) {
            InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterAssignment.class);
            node.setInterAssignment((InterAssignment) interOperation);
        }
    }

    @Override
    public void visit(KxiCoutStatement node) {
        node.setInterOperand(getOperand(false, node.getLineInfo()));
    }

    @Override
    public void visit(KxiCinStatement node) {
        node.setInterOperand(getOperand(false, node.getLineInfo()));
    }

    @Override
    public void visit(KxiIfStatement node) {
        node.setInterOperand(getOperand(false, node.getLineInfo()));
    }

    @Override
    public void visit(KxiWhileStatement node) {
        node.setInterOperand(getOperand(false, node.getLineInfo()));
    }

    @Override
    public void visit(KxiForStatement node) {
        if (node.getPostExpression() != null) valueStack.pop();
        node.setInterOperand(getOperand(false, node.getLineInfo()));
        if (node.getPreExpression() != null) valueStack.pop();
    }

    @Override
    public void visit(KxiReturnStatement node) {
        if (node.getExpression() != null)
            node.setInterOperand(getOperand(false, node.getLineInfo()));
    }

    @Override
    public void visit(KxiSwitchStatement node) {
        node.setInterOperand(getOperand(false, node.getLineInfo()));
    }


}
