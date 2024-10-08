package compilers.ast.kxi_nodes.scope;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.class_members.KxiAbstractKxiClassMember;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiClass extends AbstractKxiScope{
    private List<KxiAbstractKxiClassMember> classMemberList;
    private IdentifierToken id;

    public KxiClass(GenericListNode classMemberList, IdentifierToken id) {
        super(classMemberList, id);
        this.classMemberList = getNodeList(classMemberList);
        this.id = id;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
