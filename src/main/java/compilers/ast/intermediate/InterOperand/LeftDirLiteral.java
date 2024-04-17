package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.InterLitDir;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class LeftDirLiteral extends InterOperand{
    InterLitDir interLitDir;
    public LeftDirLiteral(InterValue interValue) {
        super(interValue);
        this.interLitDir = (InterLitDir) interValue;
    }

    @Override
    public GenericNode copy() {
        LeftDirLiteral operandLit = new LeftDirLiteral(new InterLitDir<>(interLitDir.getValue(), interLitDir.getScalarType()));
        return operandLit;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
