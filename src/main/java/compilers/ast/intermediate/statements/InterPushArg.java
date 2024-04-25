package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterOperand.InterOperand;
import compilers.ast.intermediate.expression.operation.InterUnaryOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterPushArg extends InterUnaryOperation {


    public InterPushArg(InterOperand righOperand) {
        super(righOperand);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
