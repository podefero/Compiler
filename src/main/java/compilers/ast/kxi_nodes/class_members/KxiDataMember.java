package compilers.ast.kxi_nodes.class_members;


import compilers.ast.Modifier;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.KxiVariableDeclaration;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;

public class KxiDataMember extends KxiAbstractKxiClassMember {

    private KxiVariableDeclaration variableDeclaration;

    public KxiDataMember(boolean isStatic, Modifier modifier, KxiType type, IdentifierToken id, KxiVariableDeclaration variableDeclaration) {
        super(isStatic, modifier, type, id);
        this.variableDeclaration = variableDeclaration;
    }
}
