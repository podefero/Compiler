package compilers.ast.intermediate.expression;

import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter

public class InterVariable extends InterExpression {
    private InterId interId;
    private InterOperation interOperation;

    public InterVariable(InterId interId, InterOperation interOperation) {
        super(interOperation, interId);
        this.interId = interId;
        this.interOperation = interOperation;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
