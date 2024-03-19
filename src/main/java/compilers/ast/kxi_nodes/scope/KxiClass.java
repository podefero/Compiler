package compilers.ast.kxi_nodes.scope;

import compilers.ast.kxi_nodes.class_members.KxiAbstractKxiClassMember;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiClass extends AbstractKxiScope{
    private List<KxiAbstractKxiClassMember> classMemberList;
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
        visitList(classMemberList, visit);
    }
}
