package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterIfStatement extends InterStatement {
    String label;
    private InterValue condition;
    private List<InterStatement> ifMet;

    public InterIfStatement(InterValue condition, GenericListNode ifMet, String label) {
        super(condition, ifMet);
        this.condition = condition;
        this.ifMet = getNodeList(ifMet);
        this.label = label;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
