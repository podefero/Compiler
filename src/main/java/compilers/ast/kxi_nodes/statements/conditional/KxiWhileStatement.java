package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiWhileStatement extends AbstractKxiConditionalStatement {

    public KxiWhileStatement(AbstractKxiStatement statement, AbstractKxiExpression conditionalExpression) {
        super(statement, conditionalExpression);
        this.statement = statement;
        this.conditionalExpression = conditionalExpression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
