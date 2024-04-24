package compilers.ast.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.statements.InterActivationRecord;
import compilers.ast.intermediate.statements.InterBlock;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterFunctionNode extends AbstractInterNode {
    private InterId interId;
    private InterBlock block;
    private List<String> params;
    private String returnId;
    private String pfpId;

    public InterFunctionNode(InterId interId, InterBlock block, List<String> params) {
        super(block, new InterActivationRecord((InterId) interId.copy()));
        this.interId = interId;
        this.block = block;
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
