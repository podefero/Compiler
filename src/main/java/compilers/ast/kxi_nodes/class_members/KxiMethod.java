package compilers.ast.kxi_nodes.class_members;


import compilers.ast.kxi_nodes.KxiParameter;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.Modifier;
import compilers.ast.kxi_nodes.helper.KxiMethodSuffixHelper;
import compilers.ast.kxi_nodes.helper.KxiModifierHelper;
import compilers.ast.kxi_nodes.helper.KxiStaticHelper;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.ast.kxi_nodes.token_literals.IdentifierToken;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiMethod extends KxiAbstractKxiClassMember {

    private KxiBlock block;
    private List<KxiParameter> parameters;
    private IdentifierToken id;
    private KxiType returnType;
    private Modifier modifier;
    private boolean isStatic;

    public KxiMethod(KxiMethodSuffixHelper suffixHelper, KxiType type, KxiModifierHelper kxiModifierHelper, KxiStaticHelper kxiStaticHelper) {
        super(suffixHelper.getBlock(), suffixHelper.getParameters(), suffixHelper.getId(), type, kxiModifierHelper, kxiStaticHelper);
        this.block = suffixHelper.getBlock();
        this.parameters = getNodeList(suffixHelper.getParameters());
        this.id = suffixHelper.getId();
        this.returnType = type;
        this.modifier = kxiModifierHelper.getModifier();
        this.isStatic = kxiStaticHelper.isStatic();
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
