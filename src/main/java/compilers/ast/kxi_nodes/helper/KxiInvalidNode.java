package compilers.ast.kxi_nodes.helper;

import compilers.ast.kxi_nodes.AbstractKxiNode;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Stack;

@AllArgsConstructor
public class KxiInvalidNode extends AbstractKxiNode {
    private ParserRuleContext ctx;
    private Stack<AbstractKxiNode> stackCtx;
    private RuntimeException exception;
}
