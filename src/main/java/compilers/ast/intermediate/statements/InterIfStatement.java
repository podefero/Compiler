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
    String ifTrue;
    String ifNot;
    String done;
    private List<InterStatement> ifMet;

    public InterIfStatement(GenericListNode ifMet, String label) {
        super(ifMet);
        this.ifMet = getNodeList(ifMet);
        this.label = label;
        ifTrue = convertIdToLabel(label) + "ifTrue";
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
