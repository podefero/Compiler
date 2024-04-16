package compilers.ast.intermediate.statements;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterReturn extends InterStatement {

    /**
     * Create operation that pushes R2 onto stack
     */
    public InterReturn() {

    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
