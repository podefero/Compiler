package compilers.visitor.assembly;

import compilers.ast.assembly.*;
import compilers.util.DataSizes;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.*;

@Getter
public class AssemblyAssembleVisitor extends KxiVisitorBase {
    List<String> instructions;
    Stack<String> labelStack;
    Queue<AssemblyReturnAddressDelim> returnDelimiters;
    int currentNumComments;

    public AssemblyAssembleVisitor() {
        instructions = new ArrayList<>();
        labelStack = new Stack<>();
        returnDelimiters = new ArrayDeque<>();
    }

    public String getAsm() {
        String asm = "";
        for (String code : instructions) {
            asm += code + "\n";
        }
        return asm;
    }

    String assembleCode(String label, String opCodes, Operand operandL, Operand operandR) {
        if (!labelStack.isEmpty() && label.isEmpty()) label = labelStack.pop();
        String left = operandL.getValue();
        String right = "";
        if (operandR != null) right = operandR.getValue();

        return (label + " "
                + opCodes + " "
                + left + " "
                + right);
    }

    @Override
    public void visit(AssemblyCode node) {
        instructions.add(assembleCode(node.getLabel(), node.getOpCodes(), node.getOperandL(), node.getOperandR()));
    }

    @Override
    public void visit(AssemblyDirective node) {
        String label = node.getLabel();
        String directive = node.getDirective();
        String value = node.getValue();
        instructions.add(label + " " + directive + " " + value);

    }

    @Override
    public void visit(OperandLabelWrapper node) {
        labelStack.push(node.getValue());
    }

//    @Override
//    public void visit(AssemblyComment node) {
//        instructions.add(node.getComment());
//        currentNumComments++;
//    }
//
//    @Override
//    public void visit(AssemblyNewLine node) {
//        instructions.add("\n");
//        currentNumComments++;
//    }


    @Override
    public void visit(AssemblyReturnAddressDelim node) {
        if (node.isFirstDelim()) {
            node.setStart(instructions.size());
            currentNumComments = 0;
            returnDelimiters.add(node);
        } else {
            AssemblyReturnAddressDelim startNode = returnDelimiters.poll();
            startNode.setEnd(instructions.size());
            int totalInst = (startNode.getEnd() - startNode.getStart()) + 1;
            int offset = Math.abs(totalInst - currentNumComments) * DataSizes.INSTRUCTION_SIZE;
            // currentNumComments = 0;
            instructions.add(startNode.getStart()
                    , assembleCode("", OpCodes.ADI.getValue(), new OperandReg(Registers.R15), new OperandInteger(offset)));
        }
    }
}
