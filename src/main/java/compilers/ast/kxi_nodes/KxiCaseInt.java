package compilers.ast.kxi_nodes;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.expressions.token_expression.IntLitToken;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiCaseInt extends AbstractKxiNode {
    private List<AbstractKxiStatement> statements;
    private IntLitToken caseValue;

    public KxiCaseInt(GenericListNode statements, IntLitToken caseValue) {
        super(statements, caseValue);
        this.statements = getNodeList(statements);
        this.caseValue = caseValue;
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
