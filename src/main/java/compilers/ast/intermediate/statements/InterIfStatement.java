package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterIfStatement extends InterStatement {
    String label;
    String ifNot;
    String done;
    InterStatement ifMet;
    InterElseStatement interElseStatement;

    public InterIfStatement(GenericListNode expression, InterStatement ifMet, InterElseStatement interElseStatement, String label, InterOperand interOperand) {
        super(interElseStatement, ifMet, expression);
        this.ifMet = ifMet;
        this.interOperand = interOperand;
        this.interElseStatement = interElseStatement;
        this.interExpressionList = getNodeList(expression);

        this.label = label;
        ifNot = convertIdToLabel(label) + "ifNot";
        done = convertIdToLabel(label) + "ifDone";

        if (interElseStatement != null) {
            interElseStatement.setDone(this.done);
            interElseStatement.setIfNot(this.ifNot);
        }

    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
