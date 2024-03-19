package compilers.ast.kxi_nodes.class_members;


import compilers.ast.kxi_nodes.KxiParameter;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class KxiConstructor extends KxiAbstractKxiClassMember {
    private KxiBlock block;
    private Optional<List<KxiParameter>> parameters;
    private IdentifierToken id;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    public void visitChildren(VisitKxi visitKxi) {
        id.accept(visitKxi);
        block.accept(visitKxi);
    }

}
