package compilers.ast.intermediate;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterArgs extends InterExpression {
    InterOperand interOperand;
    public InterArgs(InterOperand interOperand) {
        super(interOperand);
        this.interOperand = interOperand;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
