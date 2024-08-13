package compilers.ast.assembly;

import compilers.visitor.kxi.KxiVisitorBase;

public class OperandLabelWrapper extends Operand {
    String label;

    public OperandLabelWrapper(String label) {
        this.label = label;
        this.value = label;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
