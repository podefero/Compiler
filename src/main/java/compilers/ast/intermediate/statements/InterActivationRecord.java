package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterArgs;
import compilers.ast.intermediate.InterId;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;


@Getter
public class InterActivationRecord extends InterStatement {
    InterId functionId;

    //only pushes local variables, args are already pushed
    public InterActivationRecord(InterId functionId) {
        super(functionId);
        this.functionId = functionId;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
