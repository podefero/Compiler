package compilers.ast.intermediate.statements;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterWhileLoop extends InterStatement {
    String loop;

    public InterWhileLoop(String label) {
        this.loop = label;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }

}
