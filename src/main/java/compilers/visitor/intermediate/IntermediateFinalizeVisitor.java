package compilers.visitor.intermediate;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.InterFunctionNode;
import compilers.ast.intermediate.InterGlobal;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.ast.intermediate.statements.InterDerefStatement;
import compilers.ast.intermediate.statements.InterGlobalVariable;
import compilers.ast.intermediate.statements.InterIfStatement;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.class_members.KxiMethod;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;
import java.util.Stack;

@Getter
public class IntermediateFinalizeVisitor extends KxiVisitorBase {
    InterGlobal interGlobal;
    Stack<InterFunctionNode> interFunctionNodeStack;


    public IntermediateFinalizeVisitor(List<InterGlobalVariable> globalVariables, List<InterOperation> globalInit, List<InterFunctionNode> functions) {
        interFunctionNodeStack = new Stack<>();
        this.interGlobal = new InterGlobal(new GenericListNode(globalVariables)
                , new GenericListNode(globalInit)
                , new GenericListNode(functions));
    }

}
