package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterLit;
import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterCase extends InterStatement {
    String label;
    String exit;
    InterLit interLit;
    List<InterStatement> statements;

    public InterCase(InterLit interLit, GenericListNode block) {
        super(block, interLit);
        this.interLit = interLit;
        this.statements = getNodeList(block);
        this.label = "case_"+HashString.updateStringHash();
        this.exit = label+"_exit";
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
