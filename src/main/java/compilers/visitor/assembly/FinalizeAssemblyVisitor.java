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
    private FunctionData currentFunctionData;

    @Override
    public void visit(InterGlobal node) {
        rootNode = new AssemblyMain(new GenericListNode(getAssemblyList()));
    }
}
