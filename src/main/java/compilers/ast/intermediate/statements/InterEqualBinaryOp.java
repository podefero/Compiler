package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.expression.operation.InterBinaryOp;

public class InterEqualBinaryOp extends InterStatement {
    InterId assignmentId;
    InterBinaryOp interBinaryOp;

    /**
     * Multiple instructions
     * @param assignmentId will get address of InterId in R1 and store R2 into R1
     * @param interBinaryOp will load and add to registers (RD2)
     */
    public InterEqualBinaryOp(InterId assignmentId, InterBinaryOp interBinaryOp) {
        super(interBinaryOp, assignmentId);
        this.assignmentId = assignmentId;
        this.interBinaryOp = interBinaryOp;
    }
}
