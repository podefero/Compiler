package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterValue;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.InterOperation;
import lombok.Getter;

import java.util.List;

@Getter
public class InterIfStatement extends InterStatement {
    private InterValue condition;
    private List<InterStatement> ifMet;

    public InterIfStatement(InterValue condition, GenericListNode ifMet) {
        super(condition, ifMet);
        this.condition = condition;
        this.ifMet = getNodeList(ifMet);
    }

}
