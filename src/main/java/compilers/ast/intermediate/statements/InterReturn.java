package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterReturn extends InterStatement {
    InterOperand rightOperand;
    public InterReturn(InterOperand rightOperand) {
        super(rightOperand);
        this.rightOperand = rightOperand;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
