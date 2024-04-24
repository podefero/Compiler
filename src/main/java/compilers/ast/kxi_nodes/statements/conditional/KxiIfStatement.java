package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.ast.kxi_nodes.statements.KxiBlockStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KxiIfStatement extends AbstractKxiConditionalStatement {
    private KxiElseStatement elseStatement;


    public KxiIfStatement(KxiElseStatement elseStatement, AbstractKxiStatement statement, AbstractKxiExpression conditionalExpression) {
        super(conditionalExpression, statement, elseStatement);
        this.conditionalExpression = conditionalExpression;
        this.statement = statement;
        this.elseStatement = elseStatement;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        acceptAbstractKxi(visit);
        visit.visit(this);
    }

}
