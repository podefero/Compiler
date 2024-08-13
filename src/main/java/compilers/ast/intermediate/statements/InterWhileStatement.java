package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InterWhileStatement extends InterStatement {
    String exitLoop;
    String loop;
    InterStatement ifMet;
    List<InterExpression> preExpressions;
    List<InterExpression> postExpressions;

    public InterWhileStatement(GenericListNode preExpression , GenericListNode expression, InterStatement interIfStatements, GenericListNode postExpression ,String loop, String exitLoop) {
        super(interIfStatements, postExpression, expression, preExpression);
        this.ifMet = interIfStatements;
        this.loop = loop;
        this.exitLoop = exitLoop;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
