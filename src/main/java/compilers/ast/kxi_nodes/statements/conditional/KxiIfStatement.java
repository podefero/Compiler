package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.VisitKxi;
import lombok.Getter;

import java.util.Optional;

@Getter
public class KxiIfStatement extends AbstractKxiConditionalStatement {
    private AbstractKxiStatement elseStatement;


    public KxiIfStatement(AbstractKxiStatement elseStatement, AbstractKxiStatement statement, AbstractKxiExpression conditionalExpression) {
        super(elseStatement, statement, conditionalExpression);
        this.conditionalExpression = conditionalExpression;
        this.statement = statement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
