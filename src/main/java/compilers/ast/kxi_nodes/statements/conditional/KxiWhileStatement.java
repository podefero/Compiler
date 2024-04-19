package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.ast.kxi_nodes.statements.KxiBlockStatement;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiWhileStatement extends AbstractKxiConditionalStatement {

    String loopLabel;

    public KxiWhileStatement(KxiBlock statement, AbstractKxiExpression conditionalExpression) {
        super(statement, conditionalExpression);
        this.statement = statement;
        this.conditionalExpression = conditionalExpression;
        this.loopLabel =  HashString.updateStringHash();
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        acceptAbstractKxi(visit);
        visit.visit(this);
    }

}
