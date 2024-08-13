package compilers.ast.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.ast.intermediate.statements.EndFunctionCall;
import compilers.ast.intermediate.statements.EndPog;
import compilers.ast.intermediate.statements.InterFunctionalCall;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterGlobal extends AbstractInterNode {
    private List<InterFunctionNode> interFunctionNode;
    private List<InterOperation> globalInit;
    private List<InterStatement> interDirectives;


    public InterGlobal(GenericListNode globalData, GenericListNode globalInit, GenericListNode interFunction) {
        super(interFunction, globalInit, globalData);
        this.interDirectives = getNodeList(globalData);
        this.globalInit = getNodeList(globalInit);
        this.interFunctionNode = getNodeList(interFunction);

    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
