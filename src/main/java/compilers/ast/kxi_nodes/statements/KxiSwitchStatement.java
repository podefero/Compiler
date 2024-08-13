package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiCaseBlock;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KxiSwitchStatement extends AbstractKxiStatement {
    private KxiCaseBlock caseBlock;
    private AbstractKxiExpression expression;
    String loopLabel;
    String exitLoop;


    public KxiSwitchStatement(KxiCaseBlock caseBlock, AbstractKxiExpression expression) {
        super(caseBlock, expression);
        this.caseBlock = caseBlock;
        this.expression = expression;
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
