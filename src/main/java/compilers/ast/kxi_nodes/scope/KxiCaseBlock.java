package compilers.ast.kxi_nodes.scope;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiCaseInt;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiCaseBlock extends AbstractKxiScope {
    private List<AbstractKxiStatement> defaultStatements;
    private List<KxiCaseInt> cases;
    String loopLabel;
    String exitLoop;
    public KxiCaseBlock(GenericListNode defaultStatements, GenericListNode cases) {
        super(defaultStatements, cases);
        this.defaultStatements = getNodeList(defaultStatements);
        this.cases = getNodeList(cases);
        this.loopLabel =  HashString.updateStringHash() + "loop";
        this.exitLoop = loopLabel + "exit";
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
