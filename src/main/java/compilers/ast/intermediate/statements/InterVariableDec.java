package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.expression.InterVariable;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterVariableDec extends InterStatement {
    InterVariable interVariable;
//    List<InterExpression> interExpressionList;

    public InterVariableDec(InterVariable interVariable) {
        super(interVariable);
//        this.interExpressionList = getNodeList(expression);
        this.interVariable = interVariable;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
