package compilers.visitor.assembly;

import compilers.ast.assembly.AssemblyCode;
import compilers.ast.assembly.AssemblyComment;
import compilers.ast.assembly.Operand;
import compilers.ast.assembly.OperandLabelWrapper;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AssemblyAssembleVisitor extends KxiVisitorBase {
    List<String> instructions;
    String label;

    @Override
    public void visit(AssemblyCode node) {
        String label;
        Operand operandR = node.getOperandR();
        String valueR;

        if (operandR == null) valueR = "";
        else valueR = operandR.getValue();

        if (!this.label.isEmpty()) {
            label = this.label;
            this.label = "";
        } else label = "";

        instructions.add(label + " "
                + node.getOpCodes() + " "
                + node.getOperandL().getValue() + " "
                + valueR);
    }

    @Override
    public void visit(OperandLabelWrapper node) {
        label = node.getValue();
    }

    @Override
    public void visit(AssemblyComment node) {
        instructions.add(node.getComment());
    }


}
