package compilers.ast.assembly;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AssemblyReturnAddressDelim extends AbstractAssembly{
    private boolean firstDelim;
    String label;
    int start;
    int end;
    public AssemblyReturnAddressDelim(boolean firstDelim) {
        this.firstDelim = firstDelim;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
