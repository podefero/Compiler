package compilers.visitor.assembly;

import compilers.ast.GenericListNode;
import compilers.ast.assembly.*;
import compilers.ast.intermediate.InterGlobal;
import compilers.ast.intermediate.InterOperand.*;
import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.ast.intermediate.symboltable.FunctionData;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static compilers.ast.assembly.Directive.BYT;
import static compilers.ast.assembly.Directive.INT;
import static compilers.ast.assembly.OpCodes.LDRI;
import static compilers.ast.assembly.OpCodes.TRP;
import static compilers.ast.assembly.Registers.R1;
import static compilers.ast.assembly.Registers.R2;


@AllArgsConstructor
@Getter
public class FinalizeAssemblyVisitor extends KxiVisitorBase {
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
        if (interOperand == null) return;
        if (interSymbolTable.getFunctionDataMap().containsKey(interOperand.getInterValue().getTerminalValue())) {
            return;
        }

        if (interOperand instanceof LeftVariableStack) {
            comment("Deref " + ((LeftVariableStack) interOperand).getInterId().getId());
            twoReg(LDRI, R1, R1);
        } else if (interOperand instanceof RightVariableStack) {
            comment("Deref " + ((RightVariableStack) interOperand).getInterId().getId());
            twoReg(LDRI, R2, R2);

        } else if (interOperand instanceof RightPtr) {
            //cant deref string or id
            if (((RightPtr) interOperand).getInterPtr().getScalarType() != ScalarType.STRING) {
                comment("Deref ptr" + interOperand.getTerminalValue());
                twoReg(LDRI, R2, R2);
            }
        } else if (interOperand instanceof LeftPtr) {
            //cant deref string or id
            if (((LeftPtr) interOperand).getInterPtr().getScalarType() != ScalarType.STRING) {
                comment("Deref ptr" + interOperand.getTerminalValue());
                twoReg(LDRI, R1, R1);
            }
        }
    }

    InterValue getInterVal(List<InterExpression> expressions) {
        if (!exceptionStack.empty()) {
            InterOperation interOperation;
            if (expressions.get(0) instanceof InterOperation) {
                interOperation = (InterOperation) expressions.get(0);
                return interOperation.getRightOperand().getInterValue();
            }

        }
        return null;
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

    @Override
    public void visit(InterGlobal node) {
        rootNode = new AssemblyMain(new GenericListNode(getAssemblyList()));
    }
}
