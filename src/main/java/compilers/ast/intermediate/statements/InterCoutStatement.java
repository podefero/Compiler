package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.InterOperand.RightVariableStack;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterCoutStatement extends InterStatement {
    ScalarType scalarType;
    RightVariableStack rightVariableStack;
    public InterCoutStatement(ScalarType scalarType, RightVariableStack rightVariableStack) {
        super(rightVariableStack);
        this.scalarType = scalarType;
        this.rightVariableStack = rightVariableStack;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
