package compilers.ast.intermediate;

import compilers.ast.GenericListNode;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class InterGlobal extends AbstractInterNode {
    private  List<InterFunctionNode> interFunctionNode;
    private List<InterGlobalVariable> globalInit;
    private List<InterDirective> interDirectives;

    public InterGlobal(GenericListNode globalData, GenericListNode globalInit, GenericListNode interFunction) {
        super(globalData, globalInit, interFunction);
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
