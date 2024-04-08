package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiCaseBlockChar;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiSwitchStatementChar extends AbstractKxiStatement {
    private KxiCaseBlockChar caseBlock;
    private AbstractKxiExpression expression;

    public KxiSwitchStatementChar(KxiCaseBlockChar caseBlock, AbstractKxiExpression expression) {
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
