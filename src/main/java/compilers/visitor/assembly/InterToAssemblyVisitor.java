package compilers.visitor.assembly;

import compilers.ast.GenericListNode;
import compilers.ast.assembly.*;
import compilers.ast.intermediate.InterFunctionNode;
import compilers.ast.intermediate.InterGlobal;
import compilers.ast.intermediate.InterLit;
import compilers.ast.intermediate.InterOperand.LeftVariableStack;
import compilers.ast.intermediate.InterOperand.LeftOperandLit;
import compilers.ast.intermediate.InterOperand.RightVariableStack;
import compilers.ast.intermediate.InterOperand.RightOperandLit;
import compilers.ast.intermediate.expression.operation.InterBinaryPlus;
import compilers.ast.intermediate.statements.InterFunctionalCall;
import compilers.ast.intermediate.statements.InterVariable;
import compilers.ast.intermediate.symboltable.FunctionData;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.util.DataSizes;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    private void leftOp(OpCodes opCodes, Registers RD) {
        assemblyList.add(new AssemblyCode("", opCodes.getValue(), new OperandReg(RD), null));
    }

    private void comment(String comment) {
        assemblyList.add(new AssemblyComment(comment));
    }

    private void label(String comment) {
        assemblyList.add(new OperandLabel(comment));
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
        for (AbstractAssembly assembly : assemblyList) {
            rootNode.getAssemblyCodes().add(assembly);
        }
        System.out.println("done");
    }


    @Override
    public void visit(InterVariable node) {
        //assign R1 to result of R2
        if (node.getInterOperation() != null) {
            comment("Initializing Variable " + node.getInterId().getId());
            comment("loading " + node.getInterId().getId() + " into R1 from Stack");
            getFP();
            comment("offset ptr to var");
            decFP(interSymbolTable.getOffset(node.getInterId(), currentFunctionData));
            comment("set R1 to ptr address");
            twoReg(LDRI, R1, R14);
            comment("str R2 into R1");
            twoReg(STRI, R2, R1);
        }
    }

    @Override
    public void preVisit(InterFunctionNode node) {
        //when first enter push activation record
        currentFunctionData = interSymbolTable.getFunctionDataMap().get(node.getInterId().getId());
        label(interSymbolTable.getFunctionLabel(node.getInterId()));
    }

    @Override
    public void visit(InterFunctionalCall node) {
        comment("Zero out Record");
        getFP();
        comment("Use to zero out");
        regImmInt(MOVI, R1, 0);
        int size = currentFunctionData.getSize();
        for (int i = 0; i < size; i++) {
            decFP(i * DataSizes.INT_SIZE);
            twoReg(STRI, R1, R14); //store 0 in stack ptr
        }
        //now zeroed out push return address
        comment("R14(PFP) = FP");
        getFP();
        comment("SP = FP");
        setPFP();
        comment("Get Address");
        setPC();

        /**
         * MOV R14 FP ; PFP = FP
         * MOV FP SP ; FP = SP
         * MOV R15 PC ; Get Address
         * ADDI R15 #24
         * JMP MAIN ; call function
         * JMP END ; set return address here
         */

        assemblyList.add(new AssemblyCode("", OpCodes.JMP.getValue(), new OperandLabel(node.getCalleeId().getId()), null));
    }

    @Override
    public void visit(InterBinaryPlus node) {
        comment("Add R1 and R2, result in R2");
        twoReg(ADD, R1, R2);
    }

    @Override
    public void visit(RightVariableStack node) {
        //instruction to load variable in R2 from stack
        comment("loading " + node.getInterId().getId() + " into R2 from Stack");
        getFP();
        decFP(interSymbolTable.getOffset(node.getInterId(), currentFunctionData));
        twoReg(LDRI, R2, R14);
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
        comment("loading " + node.getInterId().getId() + " into R1 from Stack");
        getFP();
        decFP(interSymbolTable.getOffset(node.getInterId(), currentFunctionData));
        twoReg(LDRI, R1, R14);

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
