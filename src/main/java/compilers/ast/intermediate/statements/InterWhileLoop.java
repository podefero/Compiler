package compilers.ast.intermediate.statements;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class InterWhileLoop extends InterStatement {
    String label;
    String loopLabel;

    public InterWhileLoop(String label) {
        this.label = label;
        loopLabel = convertIdToLabel(label) + "loop";
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }

}
