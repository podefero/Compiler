package compilers.visitor.assembly;

import compilers.ast.GenericListNode;
import compilers.ast.assembly.*;
import compilers.ast.intermediate.InterOperand.OperandReturn;
import compilers.ast.intermediate.StackType;
import compilers.ast.intermediate.expression.operation.InterPtrAssignment;
import compilers.ast.intermediate.symboltable.ActivationRecord;
import compilers.ast.intermediate.symboltable.FunctionData;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.intermediate.symboltable.StackData;
import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.KxiEquals;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiElseStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.ast.kxi_nodes.token_literals.IntLitToken;
import compilers.ast.kxi_nodes.token_literals.TokenLiteral;
import compilers.util.DataSizes;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static compilers.ast.assembly.Directive.BYT;
import static compilers.ast.assembly.Directive.INT;
import static compilers.ast.assembly.OpCodes.*;
import static compilers.ast.assembly.Registers.*;


@AllArgsConstructor
@Getter
public class StatementsToAssemblyVisitor extends KxiVisitorBase {
    private final List<AbstractAssembly> assemblyList;
    private InterSymbolTable interSymbolTable;
    private FunctionData currentFunctionData;
    private AssemblyMain rootNode;

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


    //DIR
    private <T> void directive(Directive directive, String label, T value) {
        if (value == null) {
            assemblyList.add(new AssemblyDirective(label, directive.getValue(), ""));
        } else {
            if (directive == INT)
                directiveInt(label, (Integer) value);
            if (directive == BYT)
                directiveByte(label, (Character) value);
            if (directive == Directive.STR)
                directiveString(label, (String) value);
        }
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

    void getPre(AbstractKxiNode node) {
        List<AbstractAssembly> temp = new ArrayList<>(assemblyList);
        assemblyList.clear();
        node.getAssemblyBlock().setPreBlock(temp);
    }

    void appendBody(AbstractKxiNode node, AbstractKxiNode otherNode) {
        node.getAssemblyBlock().getBodyBlock().addAll(otherNode.getAssemblyBlock().combineBlocks());
    }

    void appendMultiBody(AbstractKxiNode node, List<? extends AbstractKxiNode> otherNode) {
        for (AbstractKxiNode n : otherNode) {
            node.getAssemblyBlock().getBodyBlock().addAll(n.getAssemblyBlock().combineBlocks());
        }
    }

    void getPost(AbstractKxiNode node) {
        List<AbstractAssembly> temp = new ArrayList<>(assemblyList);
        assemblyList.clear();
        node.getAssemblyBlock().setPostBlock(temp);
    }


    @Override
    public void preVisit(KxiMain node) {
        String label = "main_main";
        newLine();
        comment("Calling function " + "main_main");
        comment("Zero out Record");
        twoReg(MOV, R14, SP);
        regImmInt(MOVI, R0, 0);
        int size = currentFunctionData.getSize();
        for (int i = 0; i < size + 1; i++) { //i = 1 for return value
            decFP(i * DataSizes.INT_SIZE);
            twoReg(STRI, R0, R14); //store 0 in stack ptr
        }
        //now zeroed out push return address
        comment("Pre Activation Record");
        comment("PFP");
        twoReg(MOV, R13, SP);
        getFP();
        comment("Get Address, and calculate offset");
        setPC();
        regImmInt(ADI, R15, 60);
        comment("push return address");
        leftOp(PUSH, R15);
        comment("push pfp");
        leftOp(PUSH, R14);
        //instruction for DIR
        //instruction for INIT
        twoReg(MOV, FP, R13);
        regLabel(JMP, label);
        regLabel(JMP, "END");

        currentFunctionData = interSymbolTable.getFunctionDataMap().get(node.getId());
        label(label);
        newLine();
        comment("push " + node.getId() + " activation record");
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
        getPre(node);
    }

    @Override
    public void visit(KxiMain node) {
        newLine();
        label("END");
        trap(0);
        rootNode = new AssemblyMain(new GenericListNode(getAssemblyList()));
        getPost(node);
    }

    void deref(AbstractKxiExpression expression) {
        ExpressionIdLit expressionIdLit;
        if (expression instanceof ExpressionIdLit) {
            expressionIdLit = (ExpressionIdLit) expression;
            ScalarType scalarType = expressionIdLit.getScalarType();
            if (scalarType != ScalarType.STRING) {

                comment("Deref stack var");
                if (expressionIdLit.isLeft()) twoReg(LDRI, R1, R1);
                else twoReg(LDRI, R2, R2);
            }
        }
    }

    @Override
    public void preVisit(KxiIfStatement node) {
        newLine();
        comment("Set up for if statement");
        evaluateTempVar(node.getConditionalExpression());
        deref(node.getConditionalExpression());
        if (node.getElseStatement() != null)
            regAndLabel(BLT, R2, node.getIfNot());
        else
            regAndLabel(BLT, R2, node.getDone());
        comment("Use this for else");
        twoReg(MOV, R4, R2);
        getPre(node);
    }


    @Override
    public void visit(KxiIfStatement node) {
        label(node.getDone());
        rootNode = new AssemblyMain(new GenericListNode(getAssemblyList()));
        getPost(node);
    }

    @Override
    public void preVisit(KxiElseStatement node) {
        newLine();
        regLabel(JMP, node.getDone());
        comment("else statement");
        label(node.getIfNot());
        getPre(node);
    }

    @Override
    public void visit(KxiElseStatement node) {
        newLine();
        regLabel(JMP, node.getDone());
        getPost(node);
    }


    @Override
    public void visit(KxiBreakStatement interBreak) {
        newLine();
        comment("Break out of loop");
        regLabel(JMP, interBreak.getExitLoop());
        getPost(interBreak);
    }


    @Override
    public void preVisit(KxiWhileStatement node) {
        newLine();
        comment("Start of loop");
        label(node.getLoopLabel());
        comment("Set up for while loop");
        deref(node.getConditionalExpression());
        regAndLabel(BLT, R2, node.getExitLoop());
        getPre(node);
    }


    @Override
    public void visit(KxiWhileStatement node) {
        newLine();
        comment("End of while loop");
        regLabel(JMP, node.getLoopLabel());
        label(node.getExitLoop());
        getPost(node);
    }


    @Override
    public void visit(KxiReturnStatement node) {
        newLine();
        comment("Return " + currentFunctionData.getLabel());
        comment("Get ptr to Return Address in R15");
        getFP();
        comment("Deref the ptr so R15 now has the return address");
        twoReg(LDR, R15, R14);
        comment("Get PFP in R14");
        getFP();
        decFP(4);
        twoReg(LDRI, R14, R14);
        comment("SP = FP to pop activation record");
        twoReg(MOV, SP, FP);
        comment("FP = PFP ");
        twoReg(MOV, FP, R14);
        comment("push result on stack");
        deref(node.getExpression());
        leftOp(PUSH, R2);
        comment("jump to return address");
        leftOp(JMR, R15);
        getPost(node);
    }


    @Override
    public void visit(KxiVariableDeclaration node) {
        //assign R1 to result of R2
        newLine();
        comment("Initializing Variable " + node.getId());
        comment("Get ptr to var");
        getFP();
        decFP(interSymbolTable.getOffset(node.getId(), currentFunctionData));
        comment("store R2 into ptr R14");
        twoReg(STRI, R2, R14);
        getPost(node);
    }


    @Override
    public void visit(KxiCoutStatement node) {
        int trpVal;
        ScalarType scalarType = node.getScalarType();
        if (scalarType != ScalarType.CHAR && scalarType != ScalarType.STRING) trpVal = 1;
        else if (scalarType == ScalarType.STRING) trpVal = 5;
        else trpVal = 3;

        deref(node.getExpression());

        newLine();
        comment("COUT  result");
        twoReg(MOV, R3, R2);
        trap(trpVal);
        getPost(node);
    }

    @Override
    public void preVisit(KxiCinStatement node) {
        ScalarType scalarType = node.getScalarType();
        if (scalarType != ScalarType.CHAR) {
            newLine();
            comment("CIN input Integer");
            trap(2);
            twoReg(MOV, R2, R3);
        } else {
            newLine();
            comment("CIN input char");
            trap(4);
            twoReg(MOV, R2, R3);
        }
        getPre(node);
    }

    @Override
    public void preVisit(KxiSwitchStatement node) {
        newLine();
        comment("Set R5 to hold the switch value for cases");
        evaluateTempVar(node.getExpression());
        deref(node.getExpression());
        twoReg(MOV, R5, R2);
        getPre(node);
    }

    @Override
    public void visit(KxiSwitchStatement node) {
        newLine();
        comment("Exit for switch");
        label(node.getExitLoop());
        getPost(node);

    }


    @Override
    public void preVisit(KxiCaseInt node) {
        newLine();
        comment("Evaluate case " + node.getCaseValue().getValue());

        regImmInt(MOVI, R2, node.getCaseValue().getValue());
        twoReg(CMP, R2, R5);
        regAndLabel(BGT, R2, node.getExit());
        regAndLabel(BLT, R2, node.getExit());
        getPre(node);
    }

    @Override
    public void visit(KxiCaseInt node) {
        newLine();
        comment("Case block");
        label(node.getExit());
        getPost(node);

    }

    @Override
    public void preVisit(KxiCaseChar node) {
        newLine();
        comment("Evaluate case " + node.getCaseValue().getValue());

        regImmInt(MOVI, R2, node.getCaseValue().getValue());
        twoReg(CMP, R2, R5);
        regAndLabel(BGT, R2, node.getExit());
        regAndLabel(BLT, R2, node.getExit());
        getPre(node);
    }

    @Override
    public void visit(KxiCaseChar node) {
        newLine();
        comment("Case block");
        label(node.getExit());
        getPost(node);

    }

    void initTempVar(ExpressionIdLit id) {
        comment("Initializing Variable " + id.getId());
        comment("Get ptr to var");
        getFP();
        decFP(interSymbolTable.getOffset(id.getId(), currentFunctionData));
        comment("store R2 into ptr R14");
        twoReg(STRI, R2, R14);
    }

    void evaluateTempVar(AbstractKxiExpression expressionLiteral) {
        if (expressionLiteral instanceof ExpressionIdLit) {
            ExpressionIdLit node = (ExpressionIdLit) expressionLiteral;
            if (node.getScalarType() == ScalarType.STRING) {
                if (node.isLeft()) leftPtrVar(node);
                else rightPtrVar(node);
            } else {
                if (node.isLeft()) lefStackVar(node);
                else rightStackVar(node);
            }
        }
    }

    void assembleBinaryOp(OpCodes opCodes, AbstractKxiExpression expressionL, AbstractKxiExpression expressionR, ExpressionIdLit idLit) {
        evaluateTempVar(expressionL);
        evaluateTempVar(expressionR);
        deref(expressionL);
        deref(expressionR);
        twoReg(opCodes, R1, R2);
        twoReg(MOV, R2, R1);
        initTempVar(idLit);
    }




    public void rightPtrVar(ExpressionIdLit node) {
        newLine();
        comment("get ptr to " + node.getId() + " into R2 from DIR");
        regAndLabel(LDA, R2, node.getId());
    }

    public void leftPtrVar(ExpressionIdLit node) {
        newLine();
        comment("get ptr to " + node.getId() + " into R1 from DIR");
        regAndLabel(LDA, R1, node.getId());
    }


    public void rightStackVar(ExpressionIdLit node) {
        //instruction to load variable in R2 from stack
        newLine();
        String id = node.getId();
        if (interSymbolTable.getFunctionDataMap().containsKey(id)) {
            comment("Pop stack");
            leftOp(POP, R2);
        } else {
            comment("get ptr to " + node.getId() + " into R2 from Stack");
            getFP();
            decFP(interSymbolTable.getOffset(node.getId(), currentFunctionData));
            twoReg(MOV, R2, R14);
        }
    }

    public void rightLit(ExpressionLiteral node) {
        TokenLiteral interLit = node.getTokenLiteral();
        newLine();
        comment("setting R2 to " + interLit.getTerminalValue());
        if (interLit.getScalarType() == ScalarType.INT)
            regImmInt(MOVI, R2, (Integer) interLit.getValue());
        else if (interLit.getScalarType() == ScalarType.CHAR)
            regImmInt(MOVI, R2, (Character) interLit.getValue());
    }

    public void lefStackVar(ExpressionIdLit node) {
        newLine();
        String id = node.getId();
        if (interSymbolTable.getFunctionDataMap().containsKey(id)) {
            comment("Pop stack");
            leftOp(POP, R1);
        } else {
            comment("get ptr to " + node.getId() + " into R1 from Stack");
            getFP();
            decFP(interSymbolTable.getOffset(node.getId(), currentFunctionData));
            twoReg(MOV, R1, R14);
        }
    }

    public void leftLit(ExpressionLiteral node) {
        TokenLiteral interLit = node.getTokenLiteral();
        newLine();
        comment("setting R1 to " + interLit.getTerminalValue());
        if (interLit.getScalarType() == ScalarType.INT)
            regImmInt(MOVI, R1, (Integer) interLit.getValue());
        else if (interLit.getScalarType() == ScalarType.CHAR)
            regImmInt(MOVI, R1, (Character) interLit.getValue());
    }

    @Override
    public void visit(OperandReturn node) {
        if (!node.isArgumentValue()) {
            newLine();
            comment("Pop stack");
            if (node.isLeft())
                leftOp(POP, R1);
            else leftOp(POP, R2);
        }
    }
}
