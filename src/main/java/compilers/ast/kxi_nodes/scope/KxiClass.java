package compilers.ast.kxi_nodes.scope;

import compilers.ast.GenericListNode;
import compilers.ast.GenericNode;
import compilers.ast.kxi_nodes.class_members.KxiAbstractKxiClassMember;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

import java.util.List;

public class KxiClass extends AbstractKxiScope{
    private List<KxiAbstractKxiClassMember> classMemberList;
    private IdentifierToken id;

    public KxiClass(GenericListNode classMemberList, IdentifierToken id) {
        super(classMemberList, id);
        this.classMemberList = getNodeList(classMemberList);
        this.id = id;
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
