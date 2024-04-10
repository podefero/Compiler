package compilers.ast.intermediate;

import compilers.ast.GenericListNode;

import java.util.List;

public class InterMain extends AbstractInterNode {
    private List<InterInstructionNode> interInstructionList;
    private InterFunctionNode interFunction;

    public InterMain(InterFunctionNode interFunction, GenericListNode interInstructionList) {
        this.interInstructionList = getNodeList(interInstructionList);
        this.interFunction = interFunction;
    }

}
