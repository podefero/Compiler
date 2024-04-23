package compilers.ast.kxi_nodes.expressions;

import compilers.ast.intermediate.statements.InterBinaryAssignmentStatement;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KxiPostForExpression extends AbstractKxiExpression {
    private AbstractKxiExpression expression;
    private InterStatement interBinaryAssignmentStatement;

    public KxiPostForExpression(AbstractKxiExpression expression) {
        super(expression);
        this.expression = expression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
