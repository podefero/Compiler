package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.InterOperand.RightVariableStack;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterCoutStatement extends InterStatement {

    public InterCoutStatement(GenericListNode expression) {
        super(expression);
        this.interExpressionList = getNodeList(expression);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
