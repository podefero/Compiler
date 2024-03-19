package compilers.ast.kxi_nodes;

import compilers.ast.kxi_nodes.expressions.token_expression.IntLitToken;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class KxiCaseInt extends AbstractKxiNode {
    private List<AbstractKxiStatement> statements;
    private IntLitToken caseValue;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    public void visitChildren(VisitKxi visitKxi) {
        caseValue.accept(visitKxi);
        visitList(statements, visitKxi);
    }

}
