package compilers.ast.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterFunctionNode extends AbstractInterNode {
    private InterId interId;
    private List<InterStatement> statements;

    public InterFunctionNode(InterId interId, GenericListNode statements) {
        super(statements, interId);
        this.interId = interId;
        this.statements = getNodeList(statements);
    }

    //add to children
    public void pushActivationRecord() {}

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
       visitChildren(visit);
       visit.visit(this);
    }
}
