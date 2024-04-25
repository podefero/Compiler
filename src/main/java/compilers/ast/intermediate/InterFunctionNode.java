package compilers.ast.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.statements.InterActivationRecord;
import compilers.ast.intermediate.statements.InterBlock;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InterFunctionNode extends AbstractInterNode {
    private InterId interId;
    private List<InterStatement> block;
    private List<String> params;
    private String returnId;
    private String pfpId;
    private boolean main;

    public InterFunctionNode(InterId interId, GenericListNode block, List<String> params) {
        super(block);
        this.interId = interId;
        this.block = getNodeList(block);
        this.params = params;
        this.returnId = "return" + this.hashCode();
        this.pfpId = "pfp" + this.hashCode();
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
