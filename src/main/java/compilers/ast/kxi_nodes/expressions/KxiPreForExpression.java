package compilers.ast.kxi_nodes.expressions;

import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KxiPreForExpression extends AbstractKxiExpression {
    private AbstractKxiExpression expression;
    private List<InterExpression> interStatements;


    public KxiPreForExpression(AbstractKxiExpression expression) {
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
