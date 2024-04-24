package compilers.ast.kxi_nodes.statements;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.expression.InterExpression;
import compilers.ast.intermediate.statements.InterStatement;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public abstract class AbstractKxiStatement extends AbstractKxiNode {
    protected List<InterExpression> interStatements;

    public AbstractKxiStatement(GenericNode... genericNodes) {
        super(genericNodes);
    }

    @Override
    public void acceptAbstractKxi(KxiVisitorBase kxiVisitorBase) {
        kxiVisitorBase.visitStatement(this);
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        acceptAbstractKxi(visit);
    }
}
