package compilers.ast.kxi_nodes.scope;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.KxiCaseInt;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiCaseBlockInt extends AbstractKxiScope {
    private List<AbstractKxiStatement> defaultStatements;
    private List<KxiCaseInt> cases;

    public KxiCaseBlockInt(GenericListNode defaultStatements, GenericListNode cases) {
        super(defaultStatements, cases);
        this.defaultStatements = getNodeList(defaultStatements);
        this.cases = getNodeList(cases);
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
