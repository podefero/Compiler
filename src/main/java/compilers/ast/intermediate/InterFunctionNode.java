package compilers.ast.intermediate;

import compilers.ast.GenericListNode;

import java.util.List;

public class InterFunctionNode extends AbstractInterNode {
    private List<InterInstructionNode> interInstructionList;

    public InterFunctionNode(GenericListNode interInstructionList) {
        this.interInstructionList = getNodeList(interInstructionList);
    }

}
