package compilers.visitor.assembly;

import compilers.ast.assembly.*;
import compilers.ast.intermediate.InterOperand.OperandReturn;
import compilers.ast.intermediate.expression.operation.InterPtrAssignment;
import compilers.ast.intermediate.symboltable.FunctionData;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.kxi_nodes.*;
import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.KxiPostForExpression;
import compilers.ast.kxi_nodes.expressions.KxiPreForExpression;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiDiv;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiMult;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiPlus;
import compilers.ast.kxi_nodes.expressions.binary.arithmic.KxiSubtract;
import compilers.ast.kxi_nodes.expressions.binary.assignment.*;
import compilers.ast.kxi_nodes.expressions.binary.conditional.*;
import compilers.ast.kxi_nodes.expressions.literals.*;
import compilers.ast.kxi_nodes.expressions.uni.KxiNot;
import compilers.ast.kxi_nodes.expressions.uni.KxiUniSubtract;
import compilers.ast.kxi_nodes.statements.*;
import compilers.ast.kxi_nodes.statements.conditional.KxiForStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiWhileStatement;
import compilers.ast.kxi_nodes.token_literals.IntLitToken;
import compilers.ast.kxi_nodes.token_literals.TokenLiteral;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static compilers.ast.assembly.Directive.BYT;
import static compilers.ast.assembly.Directive.INT;
import static compilers.ast.assembly.OpCodes.*;
import static compilers.ast.assembly.Registers.*;


@Getter
public class ExpressionToAssemblyVisitor extends KxiVisitorBase {
    private final List<AbstractAssembly> assemblyList;
    private AbstractKxiNode currentNode;
    private InterSymbolTable interSymbolTable;
    private FunctionData currentFunctionData;

    public ExpressionToAssemblyVisitor(InterSymbolTable interSymbolTable, FunctionData currentFunctionData) {
        assemblyList = new ArrayList<>();
        this.interSymbolTable = interSymbolTable;
        this.currentFunctionData = currentFunctionData;
    }

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

    void setCurrentScope(AbstractKxiNode node) {
        if (currentNode != null) getBody(currentNode);
        currentNode = node;

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

    void getBody(AbstractKxiNode node) {
        List<AbstractAssembly> temp = new ArrayList<>(assemblyList);
        assemblyList.clear();
        node.getAssemblyBlock().setBodyBlock(temp);
    }

    void getPost(AbstractKxiNode node) {
        List<AbstractAssembly> temp = new ArrayList<>(assemblyList);
        assemblyList.clear();
        node.getAssemblyBlock().setPostBlock(temp);
    }


    void deref(AbstractKxiExpression expression, boolean left) {
        ExpressionIdLit expressionIdLit;
        if (expression instanceof ExpressionIdLit) {
            expressionIdLit = (ExpressionIdLit) expression;
            ScalarType scalarType = expressionIdLit.getScalarType();
            if (!expressionIdLit.isStringLit()) {

                comment("Deref stack var");
                if (left) twoReg(LDRI, R1, R1);
                else twoReg(LDRI, R2, R2);
            }
        }
    }

    void initTempVar(ExpressionIdLit id) {
        comment("Initializing Variable " + id.getId());
        comment("Get ptr to var");
        getFP();
        decFP(interSymbolTable.getOffset(id.getId(), currentFunctionData));
        comment("store R2 into ptr R14");
        twoReg(STRI, R2, R14);
    }

    void evaluateTempVar(AbstractKxiExpression expressionLiteral, boolean left) {
        if (expressionLiteral instanceof ExpressionIdLit) {
            ExpressionIdLit node = (ExpressionIdLit) expressionLiteral;
            if (node.getScalarType() == ScalarType.STRING) {
                if (left) leftPtrVar(node);
                else rightPtrVar(node);
            } else {
                if (left) lefStackVar(node);
                else rightStackVar(node);
            }
        }
    }

    void assembleBinaryOp(OpCodes opCodes, AbstractKxiExpression expressionL, AbstractKxiExpression expressionR, ExpressionIdLit idLit) {
        evaluateTempVar(expressionL, true);
        evaluateTempVar(expressionR, false);
        deref(expressionL, true);
        deref(expressionR, false);
        twoReg(opCodes, R1, R2);
        twoReg(MOV, R2, R1);
        initTempVar(idLit);
    }

    @Override
    public void visit(KxiMain kxiMain) {
        setCurrentScope(kxiMain);
    }

    @Override
    public void preVisit(KxiVariableDeclaration node) {
        if (node.getInitializer() != null)
            setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiIfStatement node) {
        setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiWhileStatement node) {
        setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiPreForExpression node) {
        setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiPostForExpression node) {
        setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiForStatement node) {
        setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiReturnStatement node) {
        if (node.getExpression() != null)
            setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiCoutStatement node) {
        setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiCinStatement node) {
        setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiSwitchStatement node) {
        setCurrentScope(node);
    }

    @Override
    public void preVisit(KxiExpressionStatement node) {
        setCurrentScope(node);
    }

    void binaryAssign(AbstractKxiExpression left, AbstractKxiExpression right) {
        newLine();
        comment("Binary assign");
        evaluateTempVar(left, true);
        evaluateTempVar(right, false);
        deref(right, false);
        twoReg(STRI, R2, R1);
    }


    @Override
    public void visit(KxiPlus node) {
        newLine();
        comment("Add R1 and R2, result in R2");
        assembleBinaryOp(ADD, node.getExpressionL(), node.getExpressionR(), node.getTempId());
    }

    @Override
    public void visit(KxiPlusEquals node) {
        newLine();
        comment("+= R1 and R2, result in R2");
        assembleBinaryOp(ADD, node.getExpressionL(), node.getExpressionR(), node.getTempId());
        binaryAssign(node.getExpressionL(), node.getTempId());
    }

    @Override
    public void visit(KxiSubtractEquals node) {
        newLine();
        comment("-= R1 and R2, result in R2");
        assembleBinaryOp(SUB, node.getExpressionL(), node.getExpressionR(), node.getTempId());
        binaryAssign(node.getExpressionL(), node.getTempId());
    }

    @Override
    public void visit(KxiDivEquals node) {
        newLine();
        comment("/= R1 and R2, result in R2");
        assembleBinaryOp(DIV, node.getExpressionL(), node.getExpressionR(), node.getTempId());
        binaryAssign(node.getExpressionL(), node.getTempId());
    }

    @Override
    public void visit(KxiMultEquals node) {
        newLine();
        comment("*= R1 and R2, result in R2");
        assembleBinaryOp(MUL, node.getExpressionL(), node.getExpressionR(), node.getTempId());
        binaryAssign(node.getExpressionL(), node.getTempId());
    }

    @Override
    public void visit(KxiSubtract node) {
        newLine();
        comment("Subtract R1 and R2, result in R2");
        assembleBinaryOp(SUB, node.getExpressionL(), node.getExpressionR(), node.getTempId());
    }

    @Override
    public void visit(KxiDiv node) {
        newLine();
        comment("Divide R1 and R2, result in R2");
        assembleBinaryOp(DIV, node.getExpressionL(), node.getExpressionR(), node.getTempId());
    }

    @Override
    public void visit(KxiMult node) {
        newLine();
        comment("Mult R1 and R2, result in R2");
        assembleBinaryOp(MUL, node.getExpressionL(), node.getExpressionR(), node.getTempId());
    }

    @Override
    public void visit(KxiLessThen node) {
        String hash = HashString.updateStringHash();
        String ifTrue = uniqueLabel(hash) + "_iftrue";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";

        newLine();
        comment("R1 < R2, result in R2");
        evaluateTempVar(node.getExpressionL(), true);
        evaluateTempVar(node.getExpressionR(), false);
        deref(node.getExpressionL(), true);
        deref(node.getExpressionR(), false);
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
        initTempVar(node.getTempId());
    }

    @Override
    public void visit(KxiGreaterThen node) {
        String hash = HashString.updateStringHash();
        String ifTrue = uniqueLabel(hash) + "_iftrue";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";

        newLine();
        comment("R1 > R2, result in R2");
        evaluateTempVar(node.getExpressionL(), true);
        evaluateTempVar(node.getExpressionR(), false);
        deref(node.getExpressionL(), true);
        deref(node.getExpressionR(), false);
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
        initTempVar(node.getTempId());
    }

    @Override
    public void visit(KxiEqualsEquals node) {

        String hash = HashString.updateStringHash();
        String ifTrue = uniqueLabel(hash) + "_iftrue";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";
        newLine();
        comment("R1 == R2, result in R2");
        evaluateTempVar(node.getExpressionL(), true);
        evaluateTempVar(node.getExpressionR(), false);
        deref(node.getExpressionL(), true);
        deref(node.getExpressionR(), false);
        twoReg(CMP, R1, R2);
        comment("If zero set true");
        regAndLabel(BRZ, R1, ifTrue);
        comment("Else not true");
        regAndLabel(BGT, R1, ifNot);
        regAndLabel(BLT, R1, ifNot);
        regLabel(JMP, ifNot);
        label(ifTrue);
        regImmInt(MOVI, R1, 1);
        regLabel(JMP, done);
        label(ifNot);
        regImmInt(MOVI, R1, -1);
        label(done);
        twoReg(MOV, R2, R1);
        initTempVar(node.getTempId());
    }

    @Override
    public void visit(KxiGreaterEqualsThen node) {

        String hash = HashString.updateStringHash();
        String ifTrue = uniqueLabel(hash) + "_iftrue";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";

        newLine();
        comment("R1 >= R2, result in R2");
        evaluateTempVar(node.getExpressionL(), true);
        evaluateTempVar(node.getExpressionR(), false);
        deref(node.getExpressionL(), true);
        deref(node.getExpressionR(), false);
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
        initTempVar(node.getTempId());
    }

    @Override
    public void visit(KxiLessEqualsThen node) {

        String hash = HashString.updateStringHash();
        String ifTrue = uniqueLabel(hash) + "_iftrue";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";

        newLine();
        comment("R1 <= R2, result in R2");
        evaluateTempVar(node.getExpressionL(), true);
        evaluateTempVar(node.getExpressionR(), false);
        deref(node.getExpressionL(), true);
        deref(node.getExpressionR(), false);
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
        initTempVar(node.getTempId());

    }

    @Override
    public void visit(KxiAnd node) {

        newLine();
        comment("R1 AND R2, result in R2");
        evaluateTempVar(node.getExpressionL(), true);
        evaluateTempVar(node.getExpressionR(), false);
        deref(node.getExpressionL(), true);
        deref(node.getExpressionR(), false);
        twoReg(AND, R1, R2);
        twoReg(MOV, R2, R1);
        initTempVar(node.getTempId());
    }

    @Override
    public void visit(KxiOr node) {

        newLine();
        comment("R1 OR R2, result in R2");
        evaluateTempVar(node.getExpressionL(), true);
        evaluateTempVar(node.getExpressionR(), false);
        deref(node.getExpressionL(), true);
        deref(node.getExpressionR(), false);
        twoReg(OR, R1, R2);
        twoReg(MOV, R2, R1);
        initTempVar(node.getTempId());

    }

    @Override
    public void visit(KxiNot node) {
        newLine();
        comment("R1 NOT R2, result in R2");
        regImmInt(MOVI, R1, -1);
        evaluateTempVar(node.getExpression(), false);
        deref(node.getExpression(), false);
        twoReg(NOT, R1, R2);
        twoReg(MOV, R2, R1);
        initTempVar(node.getTempId());

    }

    @Override
    public void visit(KxiNotEquals node) {
        String hash = HashString.updateStringHash();
        String ifZero = uniqueLabel(hash) + "_ifzero";
        String ifNot = uniqueLabel(hash) + "_ifnot";
        String done = uniqueLabel(hash) + "_done";
        newLine();
        comment("R1 != R2, result in R2");
        evaluateTempVar(node.getExpressionL(), true);
        evaluateTempVar(node.getExpressionR(), false);
        deref(node.getExpressionL(), true);
        deref(node.getExpressionR(), false);
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
        initTempVar(node.getTempId());

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
    public void visit(KxiUniSubtract node) {
        newLine();
        comment("Negate R2");
        evaluateTempVar(node.getExpression(), false);
        deref(node.getExpression(), false);
        regImmInt(MOVI, R0, -1);
        twoReg(MUL, R2, R0);
        twoReg(STRI, R2, R1);
        initTempVar(node.getTempId());
    }

    @Override
    public void visit(KxiEquals node) {
        //assign R1 to result of R2
        newLine();
        comment("Assignment");
        comment("str R2 into R1");
        evaluateTempVar(node.getExpressionL(), true);
        evaluateTempVar(node.getExpressionR(), false);
        deref(node.getExpressionR(), false);
        twoReg(STRI, R2, R1);

    }


//    @Override
//    public void visit(InterPushArg node) {
//        newLine();
//        comment("Pushing " + node.getRightOperand().getInterValue().getTerminalValue());
//        tryDerefInterOperand(node.getRightOperand());
//        leftOp(PUSH, R2);
//    }


    @Override
    public void visit(ExpressionBoolLit node) {
        ExpressionIntLit expressionIntLit;
        if (node.getTokenLiteral().getValue() == true)
            expressionIntLit = new ExpressionIntLit(new IntLitToken("1"));
        else
            expressionIntLit = new ExpressionIntLit(new IntLitToken("-1"));

        if (node.isLeft()) leftLit(expressionIntLit);
        else rightLit(expressionIntLit);

    }

    @Override
    public void visit(ExpressionCharLit node) {
        if (node.isLeft()) leftLit(node);
        else rightLit(node);

    }

//    @Override
//    public void visit(ExpressionIdLit node) {
//        if (node.isLeft()) lefStackVar(node);
//        else rightStackVar(node);
//    }

    @Override
    public void visit(ExpressionIntLit node) {
        if (node.isLeft()) leftLit(node);
        else rightLit(node);

    }

    @Override
    public void visit(ExpressionNullLit node) {
        ExpressionIntLit expressionIntLit;

        expressionIntLit = new ExpressionIntLit(new IntLitToken("0"));

        if (node.isLeft()) leftLit(expressionIntLit);
        else rightLit(expressionIntLit);

    }


    public void rightPtrVar(ExpressionIdLit node) {
        newLine();
        comment("get ptr to " + node.getId() + " into R2 from DIR");
        rightStackVar(node);
        //regAndLabel(LDA, R2, node.getId());
    }

    public void leftPtrVar(ExpressionIdLit node) {
        newLine();
        comment("get ptr to " + node.getId() + " into R1 from DIR");
        lefStackVar(node);
        //regAndLabel(LDA, R1, node.getId());
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
            regImmChar(MOVI, R2, (Character) interLit.getValue());
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
            regImmChar(MOVI, R1, (Character) interLit.getValue());
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
