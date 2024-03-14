package compilers.ast.kxi_nodes.scope;

import compilers.ast.kxi_nodes.class_members.KxiAbstractKxiClassMember;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KxiClass extends AbstractKxiScope{
    private List<KxiAbstractKxiClassMember> classMemberList;
    private IdentifierToken id;
}
