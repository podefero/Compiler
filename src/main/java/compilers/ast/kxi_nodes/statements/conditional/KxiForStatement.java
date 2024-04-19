package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.helper.KxiFordSemi;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.ast.kxi_nodes.statements.KxiBlockStatement;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiForStatement extends AbstractKxiConditionalStatement {
    String loopLabel;
    String exitLoop;
    private AbstractKxiExpression postExpression;
    private AbstractKxiExpression preExpression;

    public KxiForStatement(KxiBlock statement, AbstractKxiExpression postExpression, AbstractKxiExpression conditionalExpression, AbstractKxiExpression preExpression) {
        super(statement, postExpression, new KxiFordSemi(), conditionalExpression, new KxiFordSemi(), preExpression);
        this.statement = statement;
        this.postExpression = postExpression;
        this.conditionalExpression = conditionalExpression;
        this.preExpression = preExpression;
        this.loopLabel =  HashString.updateStringHash() + "loop";
        this.exitLoop = loopLabel + "exit";

    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        acceptAbstractKxi(visit);
        visit.visit(this);
    }
}
