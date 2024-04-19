package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
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
    private List<InterStatement> ifMet;
    InterElseStatement interElseStatement;

    public InterIfStatement(GenericListNode ifMet, InterElseStatement interElseStatement, String label) {
        super(interElseStatement, ifMet);
        this.ifMet = getNodeList(ifMet);
        this.interElseStatement = interElseStatement;
        this.label = label;
        ifNot = convertIdToLabel(label) + "ifNot";
        done = convertIdToLabel(label) + "ifDone";
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
