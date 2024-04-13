package compilers.ast.intermediate;

import compilers.ast.GenericListNode;
import lombok.Getter;

import java.util.List;

@Getter
public class InterMain extends AbstractInterNode {
    private InterFunctionNode interFunctionNode;
    private List<InterGlobalVariable> interGlobalVariables;
    private List<InterDirective> interDirectives;

    public InterMain(GenericListNode globalData, GenericListNode globalVars, InterFunctionNode interFunction) {
        super(globalData, globalVars, interFunction);
        this.interDirectives = getNodeList(globalData);
        this.interGlobalVariables = getNodeList(globalVars);
        this.interFunctionNode = interFunction;
    }

}
