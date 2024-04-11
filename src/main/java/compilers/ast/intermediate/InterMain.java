package compilers.ast.intermediate;

import compilers.ast.GenericListNode;
import lombok.Getter;

@Getter
public class InterMain extends AbstractInterNode {
    private InstructionChunk jmpToMain;
    private InstructionChunk blockOfCode;
    private InstructionChunk globalDirectives;
    private InstructionChunk globalInit;

    public InterMain(InstructionChunk blockOfCode, InstructionChunk jmpToMain, InstructionChunk globalInit, InstructionChunk globalDir) {
        super(blockOfCode, jmpToMain, globalDir, globalDir);
        this.jmpToMain = jmpToMain;
        this.blockOfCode = blockOfCode;
        this.globalDirectives = globalDir;
        this.globalInit = globalInit;
    }

    @Override
    public String gatherAssembly() {
        return super.gatherAssembly();
    }
}
