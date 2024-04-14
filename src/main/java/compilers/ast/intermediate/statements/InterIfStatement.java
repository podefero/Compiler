package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.InterOperation;
import lombok.Getter;

import java.util.List;

@Getter
public class InterIfStatement extends InterStatement {
    private List<InterExpression> interExpressions;
    private InterOperation branchTrue;
    private InterOperation branchFalse;
    private List<InterStatement> ifTrueBlock;
    private List<InterStatement> ifFalseBlock;

    public InterIfStatement(GenericListNode expressions, InterOperation branchTrue, InterOperation branchFalse
            , GenericListNode ifTrue, GenericListNode ifFalse) {
        super(expressions, branchTrue, branchFalse, ifTrue, ifFalse);
        this.interExpressions = getNodeList(expressions);
        this.branchTrue = branchTrue;
        this.branchFalse = branchFalse;
        this.ifTrueBlock = getNodeList(ifTrue);
        this.ifFalseBlock = getNodeList(ifFalse);
    }

}
