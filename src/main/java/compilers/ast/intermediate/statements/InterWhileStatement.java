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
    InterDerefStatement interDerefStatement;
    private List<InterStatement> ifMet;

    public InterWhileStatement(GenericListNode interIfStatements, String label, InterDerefStatement interDerefStatement) {
        super(interIfStatements,interDerefStatement);
        this.ifMet = getNodeList(interIfStatements);
        this.interDerefStatement = interDerefStatement;
        this.label = label;
        ifNot = convertIdToLabel(label) + "ifNot";
        loop = convertIdToLabel(label) + "ifDone";
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
