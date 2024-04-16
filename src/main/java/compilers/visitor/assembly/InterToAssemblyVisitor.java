package compilers.visitor.assembly;

import compilers.ast.GenericListNode;
import compilers.ast.assembly.*;
import compilers.ast.intermediate.*;
import compilers.ast.intermediate.InterOperand.*;
import compilers.ast.intermediate.expression.operation.InterAssignment;
import compilers.ast.intermediate.expression.operation.InterBinaryPlus;
import compilers.ast.intermediate.statements.*;
import compilers.ast.intermediate.symboltable.ActivationRecord;
import compilers.ast.intermediate.symboltable.FunctionData;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.intermediate.symboltable.StackData;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.util.DataSizes;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import static compilers.ast.assembly.Registers.*;
import static compilers.ast.assembly.OpCodes.*;


@AllArgsConstructor
@Getter
public class InterToAssemblyVisitor extends KxiVisitorBase {
    private final List<AbstractAssembly> assemblyList;
    private AssemblyMain rootNode;
    private InterSymbolTable interSymbolTable;
    private FunctionData currentFunctionData;

    //COMMONLY Used
    private void getFP() {
        assemblyList.add(new AssemblyCode("", OpCodes.MOV.getValue(), new OperandReg(Registers.R14), new OperandReg(Registers.FP)));
    }

    private void setPFP() {
        assemblyList.add(new AssemblyCode("", OpCodes.MOV.getValue(), new OperandReg(Registers.FP), new OperandReg(Registers.SP)));
    }

    private void setPC() {
        assemblyList.add(new AssemblyCode("", OpCodes.MOV.getValue(), new OperandReg(Registers.R15), new OperandReg(Registers.PC)));
    }

    private void decFP(int value) {
        assemblyList.add(new AssemblyCode("", OpCodes.ADI.getValue()
                , new OperandReg(Registers.R14), new OperandInteger(-value)));
    }

    private void tryDerefInterOperand(InterOperand interOperand) {
        if (interOperand instanceof LeftVariableStack) {
            comment("Deref " + ((LeftVariableStack) interOperand).getInterId().getId());
            twoReg(LDRI, R1, R1);
        } else if (interOperand instanceof RightVariableStack) {
            comment("Deref " + ((RightVariableStack) interOperand).getInterId().getId());
            twoReg(LDRI, R2, R2);
        }

    }

    //OPS
    private void twoReg(OpCodes opCodes, Registers RD, Registers RS) {
        assemblyList.add(new AssemblyCode("", opCodes.getValue(), new OperandReg(RD), new OperandReg(RS)));
    }

    private void regImmInt(OpCodes opCodes, Registers RD, int value) {
        assemblyList.add(new AssemblyCode("", opCodes.getValue(), new OperandReg(RD), new OperandInteger(value)));
    }

    private void regImmChar(OpCodes opCodes, Registers RD, char value) {
        assemblyList.add(new AssemblyCode("", opCodes.getValue(), new OperandReg(RD), new OperandChar(value)));
    }

    private void regLabel(OpCodes opCodes, String value) {
        assemblyList.add(new AssemblyCode("", opCodes.getValue(), new OperandLabel(value), null));
    }

    private void leftOp(OpCodes opCodes, Registers RD) {
        assemblyList.add(new AssemblyCode("", opCodes.getValue(), new OperandReg(RD), null));
    }

    private void trap(int value) {
        assemblyList.add(new AssemblyCode("", TRP.getValue(), new OperandInteger(value), null));
    }


    private void comment(String comment) {
        assemblyList.add(new AssemblyComment(comment));
    }

    private void label(String comment) {
        assemblyList.add(new OperandLabelWrapper(comment));
    }


    @Override
    public void preVisit(InterGlobal node) {
        AssemblyMain assemblyMain = new AssemblyMain(new GenericListNode(new ArrayList<>()));
        rootNode = assemblyMain;
        //instruction for DIR
        //instruction for INIT
    }

    @Override
    public void visit(InterGlobal node) {
        label("END");
        trap(0);
        for (AbstractAssembly assembly : assemblyList) {
            rootNode.getAssemblyCodes().add(assembly);
        }
    }


    @Override
    public void preVisit(InterFunctionNode node) {
        //when first enter push activation record
        currentFunctionData = interSymbolTable.getFunctionDataMap().get(node.getInterId().getId());
        label(interSymbolTable.getFunctionLabel(node.getInterId()));
    }

    @Override
    public void visit(InterActivationRecord node) {
        comment("push " + node.getFunctionId().getId() + " activation record");
        regImmInt(MOVI, R0, 0);
        ActivationRecord activationRecord = currentFunctionData.getActivationRecord();
        Collection<StackData> stackDataCollection = activationRecord.getStackDataMap().values();
        for (StackData stackData : stackDataCollection) {
            if (stackData.getStackType() == StackType.LOCAL) {
                comment("push " + stackData.getId());
                comment("offset: " + stackData.getOffset());
                leftOp(PUSH, R0);
            }
        }
    }

    @Override
    public void visit(InterReturn node) {
        comment("Return");
        comment("Get ptr to Return Address in R15");
        getFP();
        comment("Deref the ptr so R15 now has the return address");
        twoReg(LDR, R15, R14);
        comment("Get PFP in R14");
        getFP();
        decFP(4);
        comment("Move SP to PFP to pop activation record");
        twoReg(MOV, FP, R14);
        comment("push result on stack");
        leftOp(PUSH, R2);
        comment("jump to return address");
        leftOp(JMR, R15);

    }

    @Override
    public void visit(InterFunctionalCall node) {
        comment("Calling function " + currentFunctionData.getLabel());
        comment("Zero out Record");
        getFP();
        regImmInt(MOVI, R0, 0);
        int size = currentFunctionData.getSize();
        for (int i = 0; i < size; i++) {
            decFP(i * DataSizes.INT_SIZE);
            twoReg(STRI, R0, R14); //store 0 in stack ptr
        }
        //now zeroed out push return address
        comment("Pre Activation Record");
        comment("R14(PFP) = FP");
        getFP();
        comment("SP = FP");
        setPFP();
        comment("Get Address");
        setPC();
        int offset = interSymbolTable.getFunctionDataMap().get(node.getCalleeId().getId()).getNumParam() * DataSizes.INSTRUCTION_SIZE;
        offset += 4 * DataSizes.INSTRUCTION_SIZE; //
        comment("Offset Address by " + offset);
        regImmInt(ADI, R15, offset);
        comment("push return address");
        leftOp(PUSH, R15);
        comment("push pfp");
        leftOp(PUSH, R14);
        regLabel(JMP, currentFunctionData.getLabel());
        regLabel(JMP, "END"); //move around at some point
    }

    @Override
    public void visit(InterVariable node) {
        //assign R1 to result of R2
        if (node.getInterOperation() != null && !(node.getInterOperation() instanceof InterAssignment)) {
            comment("Initializing Variable " + node.getInterId().getId());
            comment("Get ptr to var");
            getFP();
            decFP(interSymbolTable.getOffset(node.getInterId(), currentFunctionData));
            comment("store R2 into ptr R14");
            twoReg(STRI, R2, R14);
        }
    }

    @Override
    public void visit(InterCoutStatement node) {
        int trpVal;
        if (node.getScalarType() == ScalarType.INT) trpVal = 1;
        else trpVal = 3;
        tryDerefInterOperand(node.getRightOperand());
        comment("COUT  result");
        twoReg(MOV, R3, R2);
        trap(trpVal);

    }

    @Override
    public void visit(InterBinaryPlus node) {
        comment("Add R1 and R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());

        twoReg(ADD, R2, R1);
    }

    @Override
    public void visit(InterAssignment node) {
        //assign R1 to result of R2
        comment("Assignment");
        comment("str R2 into R1");
        tryDerefInterOperand(node.getRightOperand());
        twoReg(STRI, R2, R1);
    }

    @Override
    public void visit(RightVariableStack node) {
        //instruction to load variable in R2 from stack
        comment("get ptr to " + node.getInterId().getId() + " into R2 from Stack");
        getFP();
        decFP(interSymbolTable.getOffset(node.getInterId(), currentFunctionData));
        twoReg(MOV, R2, R14);
    }

    @Override
    public void visit(RightOperandLit node) {
        InterLit interLit = node.getInterLit();
        comment("setting R2 to " + interLit.getValue());
        if (interLit.getScalarType() == ScalarType.INT)
            regImmInt(MOVI, R2, (Integer) interLit.getValue());
        else
            regImmInt(MOVI, R2, (Character) interLit.getValue());


    }

    @Override
    public void visit(LeftVariableStack node) {
        comment("get ptr to " + node.getInterId().getId() + " into R1 from Stack");
        getFP();
        decFP(interSymbolTable.getOffset(node.getInterId(), currentFunctionData));
        twoReg(MOV, R1, R14);

    }

    @Override
    public void visit(LeftOperandLit node) {
        InterLit interLit = node.getInterLit();
        comment("setting R1 to " + interLit.getValue());
        if (interLit.getScalarType() == ScalarType.INT)
            regImmInt(MOVI, R1, (Integer) interLit.getValue());
        else
            regImmInt(MOVI, R1, (Character) interLit.getValue());

    }
}
