package compilers.ast.kxi_nodes.class_members;


import compilers.ast.kxi_nodes.KxiParameter;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class KxiConstructor extends KxiAbstractKxiClassMember {
    private KxiBlock block;
    private Optional<List<KxiParameter>> parameters;
    private IdentifierToken id;

}
