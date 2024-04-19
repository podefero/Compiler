package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterWhileStatement extends InterStatement {
    String exitLoop;
    String loop;
    private List<InterStatement> ifMet;

    public InterWhileStatement(GenericListNode interIfStatements, String loop, String exitLoop) {
        super(interIfStatements);
        this.ifMet = getNodeList(interIfStatements);
        this.loop = loop;
        this.exitLoop = exitLoop;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
