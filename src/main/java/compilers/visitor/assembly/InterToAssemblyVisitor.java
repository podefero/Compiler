package compilers.visitor.assembly;

import compilers.ast.GenericListNode;
import compilers.ast.assembly.*;
import compilers.ast.intermediate.InterFunctionNode;
import compilers.ast.intermediate.InterGlobal;
import compilers.ast.intermediate.InterOperand.LeftVariableStack;
import compilers.ast.intermediate.InterOperand.LeftOperandLit;
import compilers.ast.intermediate.InterOperand.RightVariableStack;
import compilers.ast.intermediate.InterOperand.RightOperandLit;
import compilers.ast.intermediate.expression.operation.InterBinaryPlus;
import compilers.ast.intermediate.statements.InterVariable;
import compilers.ast.intermediate.symboltable.FunctionData;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class InterToAssemblyVisitor extends KxiVisitorBase {
    private final List<AbstractAssembly> assemblyList;
    private AssemblyMain rootNode;
    private InterSymbolTable interSymbolTable;
    private FunctionData currentFunctionData;
    private boolean isStack;


    @Override
    public void preVisit(InterGlobal node) {
        AssemblyMain assemblyMain = new AssemblyMain(new GenericListNode(new ArrayList<>()));
        rootNode = assemblyMain;
    }

    @Override
    public void visit(InterGlobal node) {
        for (AbstractAssembly assembly : assemblyList) {
            rootNode.getAssemblyCodes().add(assembly);
        }
    }

    @Override
    public void preVisit(InterVariable node) {
        //faster way of knowing to use LDRI or LDR
        isStack = true;
    }


    @Override
    public void preVisit(InterFunctionNode node) {
        //when a function is first entered set R14 to FP
        currentFunctionData = interSymbolTable.getFunctionDataMap().get(node.getInterId().getId());
        assemblyList.add(new OperandLabel(interSymbolTable.getFunctionLabel(node.getInterId())));
    }

    @Override
    public void visit(InterBinaryPlus node) {
    }

    @Override
    public void visit(RightVariableStack node) {
        //instruction to load variable in R2 from stack
        if (isStack) {
            //set the fp R14 FP
            assemblyList.add(new AssemblyCode("", OpCodes.MOV.getValue(), new OperandReg(Registers.R14), new OperandReg(Registers.FP)));
            //offset ptr to varId
            assemblyList.add(new AssemblyCode("", OpCodes.ADI.getValue()
                    , new OperandReg(Registers.R14), new OperandInteger(interSymbolTable.getOffset(node.getInterId(), currentFunctionData))));
            //load ptr to register R2
            assemblyList.add(new AssemblyCode("", OpCodes.LDRI.getValue(), new OperandReg(Registers.R2), new OperandReg(Registers.R14)));

        }
    }

    @Override
    public void visit(RightOperandLit node) {
    }

    @Override
    public void visit(LeftVariableStack node) {
    }

    @Override
    public void visit(LeftOperandLit node) {
    }
}
