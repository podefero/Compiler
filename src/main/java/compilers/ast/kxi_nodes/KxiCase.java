package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiCase<T> extends AbstractKxiNode {
    private List<AbstractKxiStatement> statements;
    private T caseValue;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        super.accept(visit);
        visit.visit(this);
    }

    @Override
    public void visitChildren(VisitKxi visitKxi) {

    }
}
