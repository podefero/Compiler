package compilers.visitor.assembly;

import compilers.ast.assembly.AssemblyCode;
import compilers.ast.assembly.AssemblyComment;
import compilers.ast.assembly.Operand;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AssemblyAssembleVisitor extends KxiVisitorBase {
    List<String> instructions;

    @Override
    public void visit(AssemblyCode node) {
        Operand operandR = node.getOperandR();
        String valueR;
        if (operandR == null) valueR = "";
        else valueR = operandR.getValue();

        instructions.add(node.getLabel() + " "
                + node.getOpCodes() + " "
                + node.getOperandL().getValue() + " "
                + valueR);
    }

    @Override
    public void visit(AssemblyComment node) {
        instructions.add(node.getComment());
    }


}
