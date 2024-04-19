package compilers.visitor.assembly;

import compilers.ast.GenericListNode;
import compilers.ast.assembly.*;
import compilers.ast.intermediate.*;
import compilers.ast.intermediate.InterOperand.*;
import compilers.ast.intermediate.expression.operation.*;
import compilers.ast.intermediate.statements.*;
import compilers.ast.intermediate.symboltable.ActivationRecord;
import compilers.ast.intermediate.symboltable.FunctionData;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.intermediate.symboltable.StackData;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.ast.kxi_nodes.token_literals.ThisToken;
import compilers.util.DataSizes;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static compilers.ast.assembly.Registers.*;
import static compilers.ast.assembly.Directive.*;
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

    private String uniqueLabel(String hash) {
        return currentFunctionData.getLabel() + "_" + hash;
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

    //DIR
    private <T> void directive(Directive directive, String label, T value) {
        if (directive == INT)
            directiveInt(label, (Integer) value);
        if (directive == BYT)
            directiveByte(label, (Character) value);
        if (directive == Directive.STR)
            directiveString(label, (String) value);
    }

    private void directiveInt(String label, int value) {
        assemblyList.add(new AssemblyDirective(label, INT.getValue(), new OperandInteger(value).getNumInteger()));
    }

    private void directiveByte(String label, char value) {
        assemblyList.add(new AssemblyDirective(label, BYT.getValue(), value + ""));
    }

    private void directiveString(String label, String value) {
        assemblyList.add(new AssemblyDirective(label, Directive.STR.getValue(), value));
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

    private void regAndLabel(OpCodes opCodes, Registers registers, String value) {
        assemblyList.add(new AssemblyCode("", opCodes.getValue(), new OperandReg(registers), new OperandLabel(value)));
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

    private void newLine() {
        assemblyList.add(new AssemblyNewLine());
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
        newLine();
        label("END");
        trap(0);
        for (AbstractAssembly assembly : assemblyList) {
            rootNode.getAssemblyCodes().add(assembly);
        }
    }

    @Override
    public void visit(InterGlobalVariable node) {
        newLine();
        comment("Write variable " + node.getInterId().getId() + " to Data Segment");
        if (node.getInterLit() != null)
            directive(node.getDirective(), node.convertIdToLabel(node.getInterId().getId()), node.getInterLit().getValue());

    }


    //    @Override
//    public void visit(LeftVariableDir node) {
//        newLine();
//        comment("Store address of DIR in R1");
//
//
//    }


    @Override
    public void preVisit(InterFunctionNode node) {
        //when first enter push activation record
        currentFunctionData = interSymbolTable.getFunctionDataMap().get(node.getInterId().getId());
        label(interSymbolTable.getFunctionLabel(node.getInterId()));
    }

    @Override
    public void visit(InterActivationRecord node) {
        newLine();
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
    public void visit(InterDerefStatement node) {
        tryDerefInterOperand(node.getInterOperand());
    }

    @Override
    public void preVisit(InterIfStatement node) {
        newLine();
        comment("Set up for if statement");
        if (node.getInterElseStatement() != null)
            regAndLabel(BLT, R2, node.getIfNot());
        else
            regAndLabel(BLT, R2, node.getDone());
        comment("Use this for else");
        twoReg(MOV, R4, R2);
    }


    @Override
    public void visit(InterIfStatement node) {
        label(node.getDone());
    }



    @Override
    public void visit(InterWhileLoop node) {
        newLine();
        comment("Start of loop");
        label(node.getLoopLabel());
    }

    @Override
    public void preVisit(InterWhileStatement node) {
        newLine();
        comment("Set up for while loop");
        regAndLabel(BLT, R2, node.getIfNot());
    }

    @Override
    public void visit(InterWhileStatement node) {
        newLine();
        comment("End of while loop");
        regLabel(JMP, node.getLoop());
        label(node.getIfNot());
    }

    @Override
    public void preVisit(InterElseStatement node) {
        newLine();
        comment("Set up for else statement");
        label(node.getIfNot());
        regAndLabel(BGT, R4, node.getDone());
    }

    @Override
    public void visit(InterElseStatement node) {
        newLine();
        regLabel(JMP, node.getDone());
    }

    @Override
    public void visit(InterReturn node) {
        newLine();
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
        newLine();
        comment("Calling function " + node.getCalleeId().getId());
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
            newLine();
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
        if (node.getScalarType() != ScalarType.CHAR && node.getScalarType() != ScalarType.STRING) trpVal = 1;
        else if (node.getScalarType() == ScalarType.STRING) trpVal = 5;
        else trpVal = 3;
        tryDerefInterOperand(node.getRightOperand());
        newLine();
        comment("COUT  result");
        twoReg(MOV, R3, R2);
        trap(trpVal);

    }

    @Override
    public void visit(InterBinaryPlus node) {
        newLine();
        comment("Add R1 and R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(ADD, R1, R2);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterBinarySubtract node) {
        newLine();
        comment("Subtract R1 and R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(SUB, R1, R2);
        twoReg(MOV, R2, R1);

    }

    @Override
    public void visit(InterBinaryDivide node) {
        newLine();
        comment("Divide R1 and R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(DIV, R1, R2);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterBinaryMult node) {
        newLine();
        comment("Mult R1 and R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(MUL, R1, R2);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterLogicalLessThen node) {
        String hash = HashString.updateStringHash();
        String ifTrue = uniqueLabel(hash) + "_iftrue";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";

        newLine();
        comment("R1 < R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(CMP, R1, R2);
        regAndLabel(BLT, R1, ifTrue);
        regLabel(JMP, ifNot);
        label(ifTrue);
        regImmInt(MOVI, R1, 1);
        regLabel(JMP, done);
        label(ifNot);
        regImmInt(MOVI, R1, -1);
        label(done);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterLogicalGreaterThen node) {
        String hash = HashString.updateStringHash();
        String ifTrue = uniqueLabel(hash) + "_iftrue";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";

        newLine();
        comment("R1 > R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(CMP, R1, R2);
        regAndLabel(BGT, R1, ifTrue);
        regLabel(JMP, ifNot);
        label(ifTrue);
        regImmInt(MOVI, R1, 1);
        regLabel(JMP, done);
        label(ifNot);
        regImmInt(MOVI, R1, -1);
        label(done);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterLogicalEqualsEquals node) {
        newLine();
        comment("R1 == R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(CMP, R1, R2);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterLogicalGreaterEqualThen node) {
        String hash = HashString.updateStringHash();
        String ifTrue = uniqueLabel(hash) + "_iftrue";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";

        newLine();
        comment("R1 >= R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(CMP, R1, R2);
        comment("If zero set true");
        regAndLabel(BRZ, R1, ifTrue);
        regAndLabel(BGT, R1, ifTrue);
        regLabel(JMP, ifNot);
        label(ifTrue);
        regImmInt(MOVI, R1, 1);
        regLabel(JMP, done);
        label(ifNot);
        regImmInt(MOVI, R1, -1);
        label(done);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterLogicalLessEqualThen node) {
        String hash = HashString.updateStringHash();
        String ifTrue = uniqueLabel(hash) + "_iftrue";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";

        newLine();
        comment("R1 <= R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(CMP, R1, R2);
        comment("If zero set true");
        regAndLabel(BRZ, R1, ifTrue);
        regAndLabel(BLT, R1, ifTrue);
        regLabel(JMP, ifNot);
        label(ifTrue);
        regImmInt(MOVI, R1, 1);
        regLabel(JMP, done);
        label(ifNot);
        regImmInt(MOVI, R1, -1);
        label(done);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterLogicalAnd node) {
        newLine();
        comment("R1 AND R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(AND, R1, R2);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterLogicalOr node) {
        newLine();
        comment("R1 OR R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(OR, R1, R2);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterLogicalNot node) {
        newLine();
        comment("R1 NOT R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(NOT, R1, R2);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterLogicalNotEquals node) {
        String hash = HashString.updateStringHash();
        String ifZero = uniqueLabel(hash) + "_ifzero";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";
        newLine();
        comment("R1 != R2, result in R2");
        tryDerefInterOperand(node.getLeftOperand());
        tryDerefInterOperand(node.getRightOperand());
        twoReg(CMP, R1, R2);
        regAndLabel(BRZ, R1, ifZero);
        regLabel(JMP, ifNot);
        label(ifZero);
        regImmInt(MOVI, R1, -1);
        regLabel(JMP, done);
        label(ifNot);
        regImmInt(MOVI, R1, 1);
        label(done);
        twoReg(MOV, R2, R1);
    }

    @Override
    public void visit(InterPtrAssignment node) {
        //assign R1 to address in R2
        newLine();
        comment("PTR Assignment");
        comment("Store R2 into R1");
        //tryDerefInterOperand(node.getRightOperand());
        twoReg(MOV, R1, R2);
    }

    @Override
    public void visit(InterUnarySubOperator node) {
        newLine();
        comment("get ptr to " + node.getInterId().getId() + " into R1 from Stack");
        getFP();
        decFP(interSymbolTable.getOffset(node.getInterId(), currentFunctionData));
        twoReg(MOV, R1, R14);
        comment("Negate R2");
        tryDerefInterOperand(node.getRightOperand());
        regImmInt(MOVI, R0, -1);
        twoReg(MUL, R2, R0);
        twoReg(STRI, R2, R1);
    }

    @Override
    public void visit(InterAssignment node) {
        //assign R1 to result of R2
        newLine();
        comment("Assignment");
        comment("str R2 into R1");
        tryDerefInterOperand(node.getRightOperand());
        twoReg(STRI, R2, R1);
    }

    @Override
    public void visit(RightPtr node) {
        //instruction to load variable in R2 from stack
        newLine();
        comment("get ptr to " + node.getInterPtr().getId() + " into R2 from DIR");
        regAndLabel(LDA, R2, node.convertIdToLabel(node.getInterPtr().getId()));
    }


    @Override
    public void visit(RightVariableStack node) {
        //instruction to load variable in R2 from stack
        newLine();
        comment("get ptr to " + node.getInterId().getId() + " into R2 from Stack");
        getFP();
        decFP(interSymbolTable.getOffset(node.getInterId(), currentFunctionData));
        twoReg(MOV, R2, R14);
    }

    @Override
    public void visit(RightOperandLit node) {
        InterLit interLit = node.getInterLit();
        newLine();
        comment("setting R2 to " + interLit.getTerminalValue());
        if (interLit.getScalarType() == ScalarType.INT)
            regImmInt(MOVI, R2, (Integer) interLit.getValue());
        else if (interLit.getScalarType() == ScalarType.CHAR)
            regImmInt(MOVI, R2, (Character) interLit.getValue());
    }

    @Override
    public void visit(LeftVariableStack node) {
        newLine();
        comment("get ptr to " + node.getInterId().getId() + " into R1 from Stack");
        getFP();
        decFP(interSymbolTable.getOffset(node.getInterId(), currentFunctionData));
        twoReg(MOV, R1, R14);

    }

    @Override
    public void visit(LeftOperandLit node) {
        InterLit interLit = node.getInterLit();
        newLine();
        comment("setting R1 to " + interLit.getTerminalValue());
        if (interLit.getScalarType() == ScalarType.INT)
            regImmInt(MOVI, R1, (Integer) interLit.getValue());
        else
            regImmInt(MOVI, R1, (Character) interLit.getValue());

    }
}
