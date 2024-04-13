package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.AbstractInterNode;
import compilers.ast.intermediate.InterLit;
import lombok.Getter;

import java.util.List;

@Getter
public class InterCase extends AbstractInterNode {
    InterLit interLit;
    InterIfStatement interIfStatement;
    List<InterStatements> block;

    public InterCase(InterLit interLit, InterIfStatement interIfStatement, GenericListNode block) {
        super(interLit, interIfStatement, block);
        this.interLit = interLit;
        this.interIfStatement = interIfStatement;
        this.block = getNodeList(block);
    }
}
