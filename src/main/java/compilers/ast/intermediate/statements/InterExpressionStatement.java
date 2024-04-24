package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterExpressionStatement extends InterStatement {
    List<InterExpression> interExpressionList;

    public InterExpressionStatement(GenericListNode expression) {
        super(expression);
        this.interExpressionList = getNodeList(expression);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
