package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterDerefStatement extends InterStatement {
    private InterOperand interOperand;
    public InterDerefStatement(InterOperand interOperand) {
        super(interOperand);
        this.interOperand = interOperand;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
