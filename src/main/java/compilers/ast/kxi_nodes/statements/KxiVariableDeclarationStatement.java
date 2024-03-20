package compilers.ast.kxi_nodes.statements;

import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class KxiVariableDeclarationStatement extends AbstractKxiStatement {
    KxiVariableDeclaration variableDeclaration;

    public KxiVariableDeclarationStatement(KxiVariableDeclaration variableDeclaration) {
        super(variableDeclaration);
        this.variableDeclaration = variableDeclaration;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
