package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.binary.conditional.AbstractBinaryConditionalExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;
import lombok.Getter;

import java.util.Optional;

@Getter
public class KxiForStatement extends AbstractKxiConditionalStatement {
    private AbstractKxiExpression postExpression;
    private AbstractKxiExpression preExpression;

    public KxiForStatement(AbstractKxiStatement statement, AbstractKxiExpression postExpression, AbstractKxiExpression conditionalExpression,  AbstractKxiExpression preExpression ) {
        super(statement, postExpression, conditionalExpression, preExpression);
        this.statement = statement;
        this.postExpression = postExpression;
        this.conditionalExpression = conditionalExpression;
        this.preExpression = preExpression;

    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
