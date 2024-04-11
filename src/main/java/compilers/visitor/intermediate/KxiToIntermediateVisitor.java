package compilers.visitor.intermediate;

import compilers.ast.intermediate.*;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.transform.intermediate.AbstractInterFactory;
import compilers.transform.intermediate.InterFactoryDefault;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.Stack;

@Getter
public class KxiToIntermediateVisitor extends KxiVisitorBase {
    private final Stack<AbstractInterNode> nodeStack;
    private AbstractInterNode rootNode;
    private final AbstractInterFactory factory;

    public KxiToIntermediateVisitor() {
        nodeStack = new Stack<>();
        factory = new InterFactoryDefault();
    }

    private void transformNode(AbstractKxiNode ctx) {
        AbstractInterNode node = factory.build(ctx, nodeStack);
        nodeStack.push(node);
    }

    @Override
    public void visit(KxiMain kxiMain) {
        InstructionChunk jmpToMain = new InstructionChunk();
        InstructionChunk mainBody = new InstructionChunk();
        InstructionChunk globalDir = new InstructionChunk();
        InstructionChunk globalInit = new InstructionChunk();



        jmpToMain.add(new InterInstructionNode( "", OpCodes.JMP, kxiMain.getId().getValue(), ""));
        jmpToMain.add(new InterInstructionNode( "main", OpCodes.TRP, "#0", ""));

        rootNode = new InterMain(mainBody, jmpToMain, globalInit, globalDir);

    }
}
