package compilers.ast.kxi_nodes.class_members;


import compilers.ast.Modifier;
import compilers.ast.kxi_nodes.KxiParameter;
import compilers.ast.kxi_nodes.KxiType;
import compilers.ast.kxi_nodes.expressions.token_expression.IdentifierToken;
import compilers.ast.kxi_nodes.scope.KxiBlock;

import java.util.List;

public class KxiMethod extends KxiAbstractKxiClassMember {

    private List<KxiParameter> parameters;
    private KxiBlock block;

    public KxiMethod(boolean isStatic, Modifier modifier, KxiType type, IdentifierToken id, List<KxiParameter> parameters, KxiBlock block) {
        super(isStatic, modifier, type, id);
        this.parameters = parameters;
        this.block = block;
    }
}
