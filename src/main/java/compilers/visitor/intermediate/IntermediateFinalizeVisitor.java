package compilers.visitor.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterFunctionNode;
import compilers.ast.intermediate.InterGlobal;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.ast.intermediate.statements.InterGlobalVariable;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class IntermediateFinalizeVisitor extends KxiVisitorBase {
    InterGlobal interGlobal;

    public IntermediateFinalizeVisitor(List<InterGlobalVariable> globalVariables, List<InterOperation> globalInit, List<InterFunctionNode> functions) {
        this.interGlobal = new InterGlobal(new GenericListNode(globalVariables)
                , new GenericListNode(globalInit)
                , new GenericListNode(functions));
    }


}
