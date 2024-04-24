package compilers.visitor.intermediate;

import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.InterLit;
import compilers.ast.intermediate.InterOperand.*;
import compilers.ast.intermediate.InterPtr;
import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.expression.operation.*;
import compilers.ast.intermediate.statements.InterVariable;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.expressions.binary.AbstractKxiBinaryOperation;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.*;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.visitor.kxi.KxiVisitorBase;

import java.lang.reflect.InvocationTargetException;
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
            else
                operand = new RightOperandLit(interValue);
        } else {
            if (interValue instanceof InterId) {
                operand = new LeftVariableStack(interValue);
            } else if (interValue instanceof InterPtr)
                operand = new LeftPtr(interValue);
            else
                operand = new LeftOperandLit(interValue);
        }

        return operand;
    }

    private InterOperand getOperand(boolean left, InterValue interValue) {
        InterOperand operand;
        interValue = (InterValue) interValue.copy();

        if (left) {
            if (interValue instanceof InterId) {
                operand = new LeftVariableStack(interValue);
            } else if (interValue instanceof InterPtr)
                operand = new LeftPtr(interValue);
            else
                operand = new LeftOperandLit(interValue);
        } else {
            if (interValue instanceof InterId) {
                operand = new RightVariableStack(interValue);
            } else if (interValue instanceof InterPtr)
                operand = new RightPtr(interValue);
            else
                operand = new RightOperandLit(interValue);

        }

        return operand;
    }

    private void setBinaryAssign(AbstractBinaryAssignmentExpression binaryAssign) {
        InterOperand variable = binaryAssign.getInterOperation().getLeftOperand();
        InterOperand tempVariable = getOperand(false, binaryAssign.getInterVariable().getInterId());
        InterAssignment interAssignment = new InterAssignment(tempVariable, variable);
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
    public void visit(KxiEquals node) {
        if (valueStack.size() >= 2) {
            InterOperation interOperation = getBinaryOperation(node.getLineInfo(), InterAssignment.class);
            //setBinaryOp(interOperation, node);
            node.setInterAssignment((InterAssignment) interOperation);
        }
    }
}
