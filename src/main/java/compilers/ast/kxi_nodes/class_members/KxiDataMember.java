package compilers.ast.kxi_nodes.class_members;


import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.Modifier;
import compilers.ast.kxi_nodes.helper.KxiModifierHelper;
import compilers.ast.kxi_nodes.helper.KxiStaticHelper;
import compilers.visitor.kxi.VisitKxi;
import lombok.Getter;

@Getter
public class KxiDataMember extends KxiAbstractKxiClassMember {
    private KxiVariableDeclaration variableDeclaration;
    private Modifier modifier;
    private boolean isStatic;

    public KxiDataMember(KxiVariableDeclaration kxiVariableDeclaration, KxiModifierHelper kxiModifierHelper, KxiStaticHelper kxiStaticHelper) {
        super(kxiVariableDeclaration, kxiModifierHelper, kxiStaticHelper);
        this.variableDeclaration = kxiVariableDeclaration;
        this.modifier = kxiModifierHelper.getModifier();
        this.isStatic = kxiStaticHelper.isStatic();
    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
