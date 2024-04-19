package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterWhileStatement extends InterStatement {
    String label;
    String ifNot;
    String loop;
    private List<InterStatement> ifMet;

    public InterWhileStatement(GenericListNode interIfStatements, String label) {
        super(interIfStatements);
        this.ifMet = getNodeList(interIfStatements);
        this.label = label;
        ifNot = convertIdToLabel(label) + "whileExit";
        loop = convertIdToLabel(label) + "loop";
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
