package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiCaseBlockInt;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class KxiSwitchStatementInt extends AbstractKxiStatement {
    private KxiCaseBlockInt caseBlock;
    private AbstractKxiExpression expression;

    public KxiSwitchStatementInt(KxiCaseBlockInt caseBlock, AbstractKxiExpression expression) {
        super(caseBlock, expression);
        this.caseBlock = caseBlock;
        this.expression = expression;
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
