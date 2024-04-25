package compilers.ast.kxi_nodes.statements.conditional;

import compilers.ast.kxi_nodes.expressions.AbstractKxiExpression;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.ast.kxi_nodes.statements.KxiBlockStatement;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KxiIfStatement extends AbstractKxiConditionalStatement {
    private KxiElseStatement elseStatement;
    String label;
    String ifNot;
    String done;


    public KxiIfStatement(KxiElseStatement elseStatement, AbstractKxiStatement statement, AbstractKxiExpression conditionalExpression) {
        super(elseStatement, statement, conditionalExpression);
        this.conditionalExpression = conditionalExpression;
        this.statement = statement;
        this.elseStatement = elseStatement;
        this.label = "if_" + HashString.updateStringHash();
        ifNot = label + "_ifNot";
        done = label + "_ifDone";
        if (elseStatement != null) {
            elseStatement.setDone(done);
            elseStatement.setIfNot(ifNot);
        }
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        acceptAbstractKxi(visit);
        visit.visit(this);
    }

}
