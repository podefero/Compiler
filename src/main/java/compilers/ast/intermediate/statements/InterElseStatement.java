package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class InterElseStatement extends InterStatement {
    private List<InterStatement> ifNotMet;
    private String ifNot;
    String done;

    public InterElseStatement(GenericListNode ifNotMet) {
        super(ifNotMet);
        this.ifNotMet = getNodeList(ifNotMet);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
