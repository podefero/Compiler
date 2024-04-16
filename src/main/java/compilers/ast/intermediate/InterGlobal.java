package compilers.ast.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.statements.InterFunctionalCall;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterGlobal extends AbstractInterNode {
    private List<InterFunctionNode> interFunctionNode;
    private List<InterStatement> globalInit;
    private List<InterDirective> interDirectives;
    private InterFunctionalCall functionalCall;


    public InterGlobal(GenericListNode globalData, GenericListNode globalInit, GenericListNode interFunction, InterFunctionalCall interFunctionalCall) {
        super(interFunction, interFunctionalCall, globalInit, globalData);
        this.interDirectives = getNodeList(globalData);
        this.globalInit = getNodeList(globalInit);
        this.interFunctionNode = getNodeList(interFunction);
        this.functionalCall = interFunctionalCall;

    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.preVisit(this);
        visitChildren(visit);
        visit.visit(this);
    }
}
