package compilers.ast.kxi_nodes.expressions;

import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class KxiNewExpressionArgument extends AbstractKxiExpression{
    private Optional<List<AbstractKxiExpression>> arguments;
    private IdentifierToken id;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        id.accept(visit);
        visitList(arguments, visit);
    }
}
