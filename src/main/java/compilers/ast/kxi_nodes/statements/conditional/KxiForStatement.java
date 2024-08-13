package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.expressions.KxiPostForExpression;
import compilers.ast.kxi_nodes.expressions.KxiPreForExpression;
import compilers.ast.kxi_nodes.helper.KxiFordSemi;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KxiForStatement extends AbstractKxiConditionalStatement {
    String loopLabel;
    String exitLoop;
    private KxiPostForExpression postExpression;
    private KxiPreForExpression preExpression;

    public KxiForStatement(AbstractKxiStatement statement, KxiPostForExpression postExpression, AbstractKxiExpression conditionalExpression, KxiPreForExpression preExpression) {
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
        visit.visit(this);
        acceptAbstractKxi(visit);
    }
}
