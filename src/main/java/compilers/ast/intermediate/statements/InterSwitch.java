package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterSwitch extends InterStatement {
    String exit;
    List<InterStatement> interStatements;

    public InterSwitch(GenericListNode statements, String exit) {
        super(statements);
        this.interStatements = getNodeList(statements);
        this.exit = exit;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
