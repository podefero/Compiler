package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.visitor.kxi.VisitKxi;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiVariableDeclarationStatement extends AbstractKxiStatement{
    KxiVariableDeclaration variableDeclaration;

    @Override
    public void accept(VisitKxi visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

    @Override
    protected void visitChildren(VisitKxi visit) {
        variableDeclaration.accept(visit);
    }
}
