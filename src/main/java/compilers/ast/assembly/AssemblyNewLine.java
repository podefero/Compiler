package compilers.ast.assembly;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class AssemblyNewLine extends AbstractAssembly {

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
