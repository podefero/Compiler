package compilers.ast.kxi_nodes.scope;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.statements.InterBlock;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import compilers.visitor.kxi.symboltable.SymbolTable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KxiBlock extends AbstractKxiStatement{
    private SymbolTable scope;
    private List<AbstractKxiStatement> statements;
    InterBlock interBlock;

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
