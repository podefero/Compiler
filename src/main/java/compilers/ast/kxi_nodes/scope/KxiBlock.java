package compilers.ast.kxi_nodes.scope;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiBlock extends AbstractKxiScope{
    private List<AbstractKxiStatement> statements;

    public KxiBlock(GenericListNode statements) {
        super(statements);
        this.statements = getNodeList(statements);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
