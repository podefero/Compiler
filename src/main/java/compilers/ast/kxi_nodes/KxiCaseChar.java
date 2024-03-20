package compilers.ast.kxi_nodes;

import compilers.ast.GenericListNode;
import compilers.ast.kxi_nodes.expressions.token_expression.CharLitToken;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class KxiCaseChar extends AbstractKxiNode {
    private List<AbstractKxiStatement> statements;
    private CharLitToken caseValue;

    public KxiCaseChar(GenericListNode statements, CharLitToken caseValue) {
        super(statements, caseValue);
        this.statements = getNodeList(statements);
        this.caseValue = caseValue;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }

}
