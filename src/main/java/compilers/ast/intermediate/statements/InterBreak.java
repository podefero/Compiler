package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.operation.InterOperation;

import java.util.List;

public class InterBreak extends InterStatement {
    List<InterOperation> operations;

    public InterBreak(GenericListNode ops) {
        this.operations = getNodeList(ops);
    }
}
