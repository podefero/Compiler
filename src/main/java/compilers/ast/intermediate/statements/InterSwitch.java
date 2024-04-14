package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.InterExpression;
import lombok.Getter;

import java.util.List;

@Getter
public class InterSwitch extends InterStatement {
    List<InterExpression> interExpressions;
    List<InterCase> interCases;
    InterCase caseDefault;

    public InterSwitch(GenericListNode expressions, GenericListNode cases, InterCase caseDefault) {
        super(expressions, cases, caseDefault);
        this.interExpressions = getNodeList(expressions);
        this.interCases = getNodeList(cases);
        this.caseDefault = caseDefault;
    }
}
