package compilers.ast.kxi_nodes.class_members;


import compilers.ast.kxi_nodes.Modifier;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiDataMember extends KxiAbstractKxiClassMember {
    private KxiVariableDeclaration variableDeclaration;
    private Modifier modifier;
    boolean isStatic;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    public void visitChildren(VisitKxi visitKxi) {
        variableDeclaration.accept(visitKxi);
    }

}
