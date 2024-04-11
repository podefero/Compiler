package compilers.ast.intermediate;

import compilers.ast.GenericListNode;

import java.util.List;

public class InterMain extends AbstractInterNode {
    private List<InterInstructionNode> jmpToMain;
    private List<InterInstructionNode> blockOfCode;
    private List<InterDirectiveNode> globalDirectives;
    private List<InterInstructionNode> globalInit;

    public InterMain(GenericListNode blockOfCode, GenericListNode jmpToMain, GenericListNode globalInit, GenericListNode globalDir) {
        super(blockOfCode, jmpToMain, globalDir, globalDir);
        this.jmpToMain = getNodeList(jmpToMain);
        this.blockOfCode = getNodeList(blockOfCode);
        this.globalDirectives = getNodeList(globalDir);
        this.globalInit = getNodeList(globalInit);
    }

}
