package compilers.ast.kxi_nodes.class_members;


import compilers.ast.kxi_nodes.KxiParameter;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.helper.KxiMethodSuffixHelper;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.visitor.kxi.VisitKxi;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiConstructor extends KxiAbstractKxiClassMember {
    private KxiBlock block;
    private List<KxiParameter> parameters;
    private IdentifierToken id;

    public KxiConstructor(KxiMethodSuffixHelper methodSuffixHelper) {
        super(methodSuffixHelper.getBlock(), methodSuffixHelper.getParameters(), methodSuffixHelper.getId());
        this.block = methodSuffixHelper.getBlock();
        this.parameters = getNodeList(methodSuffixHelper.getParameters());
        this.id = methodSuffixHelper.getId();

    }

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
