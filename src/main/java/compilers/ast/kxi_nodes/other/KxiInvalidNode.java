package compilers.ast.kxi_nodes.other;

import compilers.ast.AbstractKxiNode;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

@AllArgsConstructor
public class KxiInvalidNode extends AbstractKxiNode {
    private Optional<ParserRuleContext> ctx;
    private Optional<Stack<AbstractKxiNode>> stackCtx;
    private Optional<RuntimeException> exception;
}
