package compilers.ast.intermediate.statements;

import compilers.ast.GenericTerminal;
import compilers.visitor.kxi.KxiVisitorBase;

public class EndPog extends InterStatement implements GenericTerminal {
    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }

    @Override
    public String getTerminalValue() {
        return "END";
    }
}
