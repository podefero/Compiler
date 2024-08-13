package compilers.ast.intermediate.expression;

import compilers.ast.GenericNode;
import compilers.ast.assembly.Operand;
import compilers.ast.intermediate.AbstractInterNode;
import lombok.Getter;

@Getter
public abstract class InterExpression extends AbstractInterNode {
    public InterExpression(GenericNode... genericNodes) {
        super(genericNodes);
    }


}
