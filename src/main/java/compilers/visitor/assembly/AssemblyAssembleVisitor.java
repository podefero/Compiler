package compilers.visitor.assembly;

import compilers.ast.assembly.AssemblyCode;
import compilers.ast.assembly.AssemblyComment;
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
        instructions.add(node.getLabel() + " "
        + node.getOpCodes() + " "
        + node.getOperandL().getValue() + " "
        + node.getOperandR().getValue());
    }

    @Override
    public void visit(AssemblyComment node) {
        instructions.add(node.getComment());
    }


}
