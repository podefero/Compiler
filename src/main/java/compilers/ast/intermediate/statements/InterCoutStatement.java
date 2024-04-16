package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.InterOperand.RightVariableStack;
import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterCoutStatement extends InterStatement {
    ScalarType scalarType;
    InterOperand rightOperand;
    public InterCoutStatement(ScalarType scalarType, InterOperand rightOperand) {
        super(rightOperand);
        this.scalarType = scalarType;
        this.rightOperand = rightOperand;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
