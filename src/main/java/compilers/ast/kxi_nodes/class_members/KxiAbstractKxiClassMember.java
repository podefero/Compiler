package compilers.ast.kxi_nodes.class_members;

import compilers.ast.AbstractKxiNode;
import compilers.ast.Modifier;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.expressions.token_expression.TokenType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class KxiAbstractKxiClassMember extends AbstractKxiNode {
    protected boolean isStatic;
    protected Modifier modifier;
    protected KxiType type;
    protected IdentifierToken id;

}
