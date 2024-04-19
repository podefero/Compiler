package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterElseStatement extends InterStatement {
    String ifNot;
    String done;
    private List<InterStatement> ifNotMet;

    public InterElseStatement(GenericListNode ifNotMet, String label) {
        super(ifNotMet);
        this.ifNotMet = getNodeList(ifNotMet);
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
