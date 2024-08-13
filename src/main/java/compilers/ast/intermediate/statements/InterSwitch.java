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
    List<InterCase> interCaseList;
    List<InterStatement> defaultStatements;

    public InterSwitch(GenericListNode expression, GenericListNode cases, GenericListNode statements, String exit) {
        super(statements, cases, expression);
        this.interCaseList = getNodeList(cases);
        this.defaultStatements = getNodeList(statements);
        this.exit = exit;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
