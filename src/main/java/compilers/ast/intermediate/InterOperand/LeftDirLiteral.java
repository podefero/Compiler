package compilers.ast.intermediate.InterOperand;

import compilers.ast.GenericNode;
import compilers.ast.assembly.Directive;
import compilers.ast.intermediate.InterLitDir;
import compilers.ast.intermediate.InterValue;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class LeftDirLiteral extends InterOperand{
    InterLitDir interLitDir;
    Directive directive;
    public LeftDirLiteral(InterValue interValue, Directive directive) {
        super(interValue);
        this.interLitDir = (InterLitDir) interValue;
        this.directive = directive;
    }

    @Override
    public GenericNode copy() {
        LeftDirLiteral operandLit = new LeftDirLiteral(new InterLitDir<>(interLitDir.getValue(), interLitDir.getScalarType(), directive), directive);
        return operandLit;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
