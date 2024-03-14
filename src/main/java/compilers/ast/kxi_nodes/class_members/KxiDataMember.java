package compilers.ast.kxi_nodes.class_members;


import compilers.ast.Modifier;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KxiDataMember extends KxiAbstractKxiClassMember {

    private KxiVariableDeclaration variableDeclaration;
    private Modifier modifier;
    boolean isStatic;

}
