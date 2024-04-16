package compilers.ast.intermediate.statements;

import compilers.ast.kxi_nodes.ScalarType;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterCoutStatement extends InterStatement {
    ScalarType scalarType;

    public InterCoutStatement(ScalarType scalarType) {
        this.scalarType = scalarType;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
