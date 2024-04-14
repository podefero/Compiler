package compilers.ast.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.ast.intermediate.statements.InterStatement;
import lombok.Getter;

import java.util.List;

@Getter
public class InterFunctionNode extends AbstractInterNode {
    private InterId interId;
    private List<InterOperation> activationRecord;
    private List<InterStatement> statements;
   // private InterReturn interReturn;

    public InterFunctionNode(InterId interId, GenericListNode activationRecord, GenericListNode statements) {
        super(statements, activationRecord, interId);
        this.interId = interId;
        this.activationRecord = getNodeList(activationRecord);
        this.statements = getNodeList(statements);
        //this.interReturn = interReturn;
    }

}
