package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterReturnOperand;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InterMethodExpression extends InterStatement {
    List<InterPushArg> interPushArgList;

    public InterMethodExpression(GenericListNode args) {
        this.interPushArgList = getNodeList(args);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
