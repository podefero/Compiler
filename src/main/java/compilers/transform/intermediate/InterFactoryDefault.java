package compilers.transform.intermediate;

import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.InterInstructionNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.transform.AbstractFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InterFactoryDefault extends AbstractInterFactory {

    @Override
    public AbstractInterNode build(AbstractKxiNode ctx, Stack<AbstractInterNode> stack) {
        if(ctx instanceof KxiMain) {
            List<InterInstructionNode> jmpToMain = new ArrayList<>();

        }

        return null;
    }
}
