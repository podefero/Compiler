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
import compilers.ast.intermediate.statements.InterVariable;
import compilers.ast.intermediate.symboltable.FunctionData;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class InterToAssemblyVisitor extends KxiVisitorBase {
    private final List<AbstractAssembly> assemblyList;
    private AssemblyMain rootNode;
    private InterSymbolTable interSymbolTable;
    private FunctionData currentFunctionData;

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
        System.out.println("done");
    }

    @Override
    public void visit(InterVariable node) {
        //assign R1 to result of R2
        if (node.getInterOperation() != null) {
            assemblyList.add(new AssemblyComment("Initializing Variable " + node.getInterId().getId()));
            assemblyList.add(new AssemblyComment("loading " + node.getInterId().getId() + " into R1 from Stack"));
            //set the fp R14 FP
            assemblyList.add(new AssemblyCode("", OpCodes.MOV.getValue(), new OperandReg(Registers.R14), new OperandReg(Registers.FP)));
            //offset ptr to varId
            assemblyList.add(new AssemblyComment("offset ptr to var"));
            assemblyList.add(new AssemblyCode("", OpCodes.ADI.getValue()
                    , new OperandReg(Registers.R14), new OperandInteger(interSymbolTable.getOffset(node.getInterId(), currentFunctionData))));
            assemblyList.add(new AssemblyComment("set R1 to ptr address"));
            assemblyList.add(new AssemblyCode("", OpCodes.LDRI.getValue(), new OperandReg(Registers.R1), new OperandReg(Registers.R14)));
            //str R2 into R1
            assemblyList.add(new AssemblyComment("str R2 into R1"));
            assemblyList.add(new AssemblyCode("", OpCodes.STRI.getValue(), new OperandReg(Registers.R2), new OperandReg(Registers.R1)));
        }
    }

    @Override
    public void preVisit(InterFunctionNode node) {
        //when first enter push activation record
        currentFunctionData = interSymbolTable.getFunctionDataMap().get(node.getInterId().getId());
        assemblyList.add(new OperandLabel(interSymbolTable.getFunctionLabel(node.getInterId())));
    }

    @Override
    public void visit(InterBinaryPlus node) {
        assemblyList.add(new AssemblyComment("Add R1 and R2, result in R2"));
        assemblyList.add(new AssemblyCode("", OpCodes.ADD.getValue(), new OperandReg(Registers.R1), new OperandReg(Registers.R2)));
    }

    @Override
    public void visit(RightVariableStack node) {
        //instruction to load variable in R2 from stack
        assemblyList.add(new AssemblyComment("loading " + node.getInterId().getId() + " into R2 from Stack"));
        //set the fp R14 FP
        assemblyList.add(new AssemblyCode("", OpCodes.MOV.getValue(), new OperandReg(Registers.R14), new OperandReg(Registers.FP)));
        //offset ptr to varId
        assemblyList.add(new AssemblyCode("", OpCodes.ADI.getValue()
                , new OperandReg(Registers.R14), new OperandInteger(interSymbolTable.getOffset(node.getInterId(), currentFunctionData))));
        //load ptr to register R2
        assemblyList.add(new AssemblyCode("", OpCodes.LDRI.getValue(), new OperandReg(Registers.R2), new OperandReg(Registers.R14)));


    }

    @Override
    public void visit(RightOperandLit node) {
        InterLit interLit = node.getInterLit();
        assemblyList.add(new AssemblyComment("setting R2 to " + interLit.getValue()));
        if (interLit.getScalarType() == ScalarType.INT)
            assemblyList.add(new AssemblyCode("", OpCodes.MOVI.getValue(), new OperandReg(Registers.R2)
                    , new OperandInteger((Integer) interLit.getValue())));
        else
            assemblyList.add(new AssemblyCode("", OpCodes.MOVI.getValue(), new OperandReg(Registers.R2)
                    , new OperandChar((Character) interLit.getValue())));

    }

    @Override
    public void visit(LeftVariableStack node) {
        //instruction to load variable in R1 from stack
        assemblyList.add(new AssemblyComment("loading " + node.getInterId().getId() + " into R1 from Stack"));
        //set the fp R14 FP
        assemblyList.add(new AssemblyCode("", OpCodes.MOV.getValue(), new OperandReg(Registers.R14), new OperandReg(Registers.FP)));
        //offset ptr to varId
        assemblyList.add(new AssemblyCode("", OpCodes.ADI.getValue()
                , new OperandReg(Registers.R14), new OperandInteger(interSymbolTable.getOffset(node.getInterId(), currentFunctionData))));
        //load ptr to register R1
        assemblyList.add(new AssemblyCode("", OpCodes.LDRI.getValue(), new OperandReg(Registers.R1), new OperandReg(Registers.R14)));

    }

    @Override
    public void visit(LeftOperandLit node) {
        InterLit interLit = node.getInterLit();
        assemblyList.add(new AssemblyComment("setting R1 to " + interLit.getValue()));
        if (interLit.getScalarType() == ScalarType.INT)
            assemblyList.add(new AssemblyCode("", OpCodes.MOVI.getValue(), new OperandReg(Registers.R1)
                    , new OperandInteger((Integer) interLit.getValue())));
        else
            assemblyList.add(new AssemblyCode("", OpCodes.MOVI.getValue(), new OperandReg(Registers.R1)
                    , new OperandChar((Character) interLit.getValue())));

    }
}
