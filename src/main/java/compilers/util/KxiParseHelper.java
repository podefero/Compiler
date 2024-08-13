package compilers.util;

import compilers.antlr.KxiParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class KxiParseHelper {

    public TerminalNode findFirstTerminal(List<ParseTree> children) {
        return (TerminalNode) children.stream()
                .filter(node -> node instanceof TerminalNode)
                .findFirst()
                .get();
    }

    public String getSymbolicName(TerminalNode node) {
        return KxiParser.VOCABULARY.getSymbolicName(node.getSymbol().getType());
    }

    public int getTokenType(TerminalNode node) {
        return node.getSymbol().getType();
    }


}
