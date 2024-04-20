package compilers.ast.intermediate.statements;

import compilers.visitor.kxi.KxiVisitorBase;

public class EndPog extends InterStatement{
    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
