package compilers.visitor.assembly;

import compilers.ast.assembly.*;
import compilers.ast.kxi_nodes.KxiMain;
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
    public void visit(AssemblyDirective node) {
        String label = node.getLabel();
        String directive = node.getDirective();
        String value = node.getValue();
        instructions.add(label + " " + directive + " " + value);

    }

    @Override
    public void visit(OperandLabelWrapper node) {
        label = node.getValue();
    }

    @Override
    public void visit(AssemblyComment node) {
        instructions.add(node.getComment());
    }

    @Override
    public void visit(AssemblyNewLine node) {
        instructions.add("\n");
    }
}
