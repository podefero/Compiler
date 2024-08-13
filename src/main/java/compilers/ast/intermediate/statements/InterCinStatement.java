package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.operation.InterAssignment;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterCinStatement extends InterStatement {
    public InterCinStatement(GenericListNode expression) {
        super(expression);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
