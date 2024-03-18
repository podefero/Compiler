package compilers.ast.kxi_nodes.class_members;


import compilers.ast.kxi_nodes.Modifier;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiDataMember extends KxiAbstractKxiClassMember {

    private KxiVariableDeclaration variableDeclaration;
    private Modifier modifier;
    boolean isStatic;

}
