package compilers.ast.kxi_nodes;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.ast.kxi_nodes.token_literals.IntLitToken;
import compilers.util.HashString;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiCaseInt extends AbstractKxiNode {
    private List<AbstractKxiStatement> statements;
    private IntLitToken caseValue;
    String label;
    String fall;
    String exit;

    public KxiCaseInt(GenericListNode statements, IntLitToken caseValue) {
        super(statements, caseValue);
        this.statements = getNodeList(statements);
        this.caseValue = caseValue;
        this.label = "case_"+ HashString.updateStringHash();
        this.exit = label+"_exit";
        this.fall = label+"_fall";
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
