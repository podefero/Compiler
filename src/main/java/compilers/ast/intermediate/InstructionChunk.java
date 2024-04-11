package compilers.ast.intermediate;

import java.util.ArrayList;
import java.util.List;

public class InstructionChunk extends AbstractInterNode {
    private List<InterInstructionNode> instructionNodeList;

    public InstructionChunk() {
        instructionNodeList = new ArrayList<>();
    }

    public void add(InterInstructionNode interInstructionNode) {
        instructionNodeList.add(interInstructionNode);
    }

    @Override
    public String gatherAssembly() {
        String body = "";
        for (InterInstructionNode node : instructionNodeList) {
            body += node.getAssembly() + "\n";
        }
        return body;
    }
}
