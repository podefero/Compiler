package compilers.visitor.intermediate;

import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.InterFunctionNode;
import compilers.ast.intermediate.InterInstructionNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.transform.intermediate.AbstractInterFactory;
import compilers.transform.intermediate.InterFactoryDefault;
import compilers.visitor.kxi.KxiVisitorBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        List<InterInstructionNode> jmpToMain = new ArrayList<>();

    }
}
