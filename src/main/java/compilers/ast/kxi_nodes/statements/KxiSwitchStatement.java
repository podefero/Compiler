package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiSwitchStatement extends AbstractKxiStatement {
    private KxiCaseBlock caseBlock;
    private AbstractKxiExpression expression;

    public KxiSwitchStatement(KxiCaseBlock caseBlock, AbstractKxiExpression expression) {
        super(caseBlock, expression);
        this.caseBlock = caseBlock;
        this.expression = expression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        acceptAbstractKxi(visit);
        visit.visit(this);
    }
}
