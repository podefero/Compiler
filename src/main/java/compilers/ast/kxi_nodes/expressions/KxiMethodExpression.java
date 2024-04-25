package compilers.ast.kxi_nodes.expressions;

import compilers.ast.intermediate.InterArgs;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.kxi_nodes.KxiArguments;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KxiMethodExpression extends AbstractKxiExpression{
    private KxiArguments arguments;
    private AbstractKxiExpression methodExpression;
    List<InterExpression> interExpressions;

    public KxiMethodExpression(KxiArguments arguments, AbstractKxiExpression methodExpression) {
        super(arguments, methodExpression);
        this.arguments = arguments;
        this.methodExpression = methodExpression;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
