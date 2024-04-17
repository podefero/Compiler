package compilers.ast.assembly;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class AssemblyDirective extends AbstractAssembly {
    private String label;
    private String directive;
    private String value;

    public AssemblyDirective(String label, String directive, String value) {
        this.label = label;
        this.directive = directive;
        this.value = value;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
